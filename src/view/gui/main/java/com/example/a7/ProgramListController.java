package com.example.a7;

import controller.Controller;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import model.ProgramState;
import model.exceptions.InterpreterException;
import model.statements.IStatement;
import repository.IRepository;
import repository.Repository;
import view.Examples;

import java.util.ArrayList;
import java.util.List;

public class ProgramListController {
    public void setProgramController(ProgramController programController) {
        this.programController = programController;
    }

    private ProgramController programController;

    @FXML
    private ListView<IStatement> programList;

    @FXML
    private Button executeButton;
    private Examples examples;

    public void initialize() throws InterpreterException {
        try {
            this.examples = new Examples();
            examples.addExamples();

            programList.setItems(FXCollections.observableArrayList(examples.getExamples()));
        }
        catch (InterpreterException interpreterException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, interpreterException.getMessage(), ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }

    public void handleExecuteButton(ActionEvent actionEvent) {
        int index = programList.getSelectionModel().getSelectedIndex();
        if (index < 0)
            return;
        ProgramState state = new ProgramState(this.examples.getAt(index));
        IRepository repository = new Repository("log" + Integer.toString(index+1) + ".txt");
        Controller controller = new Controller(repository);
        controller.addProgram(state);
        programController.setController(controller);
    }
}
