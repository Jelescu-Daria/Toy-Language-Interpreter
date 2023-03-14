package com.example.a7;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ProgramState;
import model.adts.*;
import model.exceptions.InterpreterException;
import model.statements.IStatement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(T1 f, T2 s) {
        this.first = f;
        this.second = s;
    }


    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }
}

public class ProgramController {
    private ExecutorService executorService;
    private Controller controller;

    @FXML
    private TextField noOfProgramStates;

    @FXML
    private Button oneStepButton;

    @FXML
    private ListView<String> exeStack;

    @FXML
    private TableView<Pair<String, Value>> symTable;

    @FXML
    private TableColumn<Pair<String, Value>, String> symTableVarNameCol;

    @FXML
    private TableColumn<Pair<String, Value>, String> symTableVarValueCol;

    @FXML
    private ListView<Integer> programStatesIDs;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapTableAddressCol;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> heapTableValueCol;

    @FXML
    private ListView<String> output;

    @FXML
    private TableView<Pair<Integer, Value>> heapTable;

    public void initialize() {
        symTableVarNameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getFirst()));
        symTableVarValueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSecond().toString()));
        heapTableAddressCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        heapTableValueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSecond().toString()));

    }

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    public void oneStepButtonHandler() throws InterruptedException {
        if(controller == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "A program needs to be selected in order to execute it.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        populate();
        if (getCurrentProgramState() == null || getCurrentProgramState().getExeStack().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There is nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = controller.removeCompletedProgram(controller.getProgramStates());
        ProgramState state = programStateList.get(0);
        state.getHeap().setContent(
                controller.garbageCollector(
                        controller.getAddrFromSymTable(
                                programStateList.stream().map(programState ->  programState.getSymTable().getContent().values()).collect(Collectors.toList()),
                                state.getHeap().getContent()
                        ),
                        state.getHeap().getContent()
                ));
        controller.oneStepForAllPrograms(programStateList, executorService);
        programStateList = controller.removeCompletedProgram(controller.getProgramStates());
        executorService.shutdownNow();
        populate();
        controller.setProgramList(programStateList);
    }

    private ProgramState getCurrentProgramState(){
        if (controller.getProgramStates().size() == 0)
            return null;
        int currentId = programStatesIDs.getSelectionModel().getSelectedIndex();
        if (currentId == -1 || currentId >= controller.getProgramStates().size())
            return controller.getProgramStates().get(0);
        return controller.getProgramStates().get(currentId);
    }

    @FXML
    private void populate() {
        populateHeap();
        populateProgramStatesIDs();
        populateFileTable();
        populateOutput();
        populateSymbolTable();
        populateExecutionStack();
    }

    private void populateHeap() {
        MyIHeap<Value> heap;
        if (!controller.getProgramStates().isEmpty())
            heap = controller.getProgramStates().get(0).getHeap();
        else heap = new MyHeap<Value>();
        List<Pair<Integer,Value>> heapTableList = new ArrayList<>();
        for (Map.Entry<Integer, Value> entry :  heap.getContent().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTable.setItems(FXCollections.observableList(heapTableList));
        heapTable.refresh();
    }

    private void populateProgramStatesIDs() {
        List<ProgramState> programStates = controller.getProgramStates();
        var idList = programStates.stream().map(ps -> ps.id).collect(Collectors.toList());
        programStatesIDs.setItems(FXCollections.observableList(idList));
        noOfProgramStates.setText("" + programStates.size());
    }

    private void populateFileTable() {
        ArrayList<StringValue> files;
        if (controller.getProgramStates().size() > 0)
            files = new ArrayList<>(controller.getProgramStates().get(0).getFileTable().getKeySet());
        else files = new ArrayList<>();
        List<String> filesString = files.stream().map(StringValue::getValue).collect(Collectors.toList());
        fileTable.setItems(FXCollections.observableArrayList(filesString));
    }

    private void populateOutput() {
        MyIList<String> out;
        if (!controller.getProgramStates().isEmpty())
            out = controller.getProgramStates().get(0).getOutAsString();
        else out = new MyList<>();
        output.setItems(FXCollections.observableList(out.getList()));
        output.refresh();
    }

    private void populateSymbolTable() {
        ProgramState state = getCurrentProgramState();
        List<Pair<String, Value>> symbolTableList = new ArrayList<>();
        if (state != null)
            for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symTable.setItems(FXCollections.observableList(symbolTableList));
        symTable.refresh();
    }

    private void populateExecutionStack() {
        ProgramState state = getCurrentProgramState();
        List<String> executionStackListAsString = new ArrayList<>();
        if (state != null)
            for(IStatement s : state.getExeStack().getReversed()){
                executionStackListAsString.add(s.toString());
            }
        exeStack.setItems(FXCollections.observableList(executionStackListAsString));
        exeStack.refresh();
    }
 }
