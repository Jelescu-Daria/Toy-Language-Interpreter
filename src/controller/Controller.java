package controller;

import model.ProgramState;
import model.adts.MyIStack;
import model.exceptions.InterpreterException;
import model.statements.IStatement;
import model.values.RefValue;
import model.values.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void oneStepForAllPrograms(List<ProgramState> programStates, ExecutorService executorService) throws InterruptedException {
        programStates.forEach(prg -> {
            try {
                repository.logProgramStateExec(prg);
            } catch (InterpreterException | IOException e) {
                System.out.println(e.getMessage());
            }
        });
        List<Callable<ProgramState>> callableList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep)).toList();
        List<ProgramState> newProgramStates = executorService.invokeAll(callableList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        if (e.getCause() instanceof InterpreterException)
                            return null;
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull).toList();
        programStates.addAll(newProgramStates);
        programStates.forEach(prg -> {
            try {
                repository.logProgramStateExec(prg);
            } catch (InterpreterException | IOException e) {
                System.out.println(e.getMessage());}
        });
        repository.setProgramList(programStates);
    }

    public void allStep() throws InterpreterException, IOException, InterruptedException {
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedProgram(repository.getProgramList());
        while (programStateList.size() > 0) {
            ProgramState state = programStateList.get(0);
            state.getHeap().setContent(
                    garbageCollector(
                            getAddrFromSymTable(
                                    programStateList.stream().map(programState ->  programState.getSymTable().getContent().values()).collect(Collectors.toList()),
                                    state.getHeap().getContent()
                            ),
                            state.getHeap().getContent()
                    ));
            oneStepForAllPrograms(programStateList, executorService);
            programStateList = removeCompletedProgram(repository.getProgramList());
        }
        executorService.shutdownNow();
        repository.setProgramList(programStateList);
    }

    public Map<Integer, Value> garbageCollector(Set<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Set<Integer> getAddrFromSymTable(List<Collection<Value>> symTableValues, HashMap<Integer, Value> heap){
        Set<Integer> addresses = new TreeSet<>();
        symTableValues.forEach(symTable -> symTable.stream()
                .filter(v -> v instanceof RefValue)
                .forEach(v -> {
                    while (v instanceof RefValue) {
                        addresses.add(((RefValue) v).getAddress());
                        v = heap.get(((RefValue) v).getAddress());
                    }
                }
        ));
        return addresses;
    }

    public List<ProgramState> removeCompletedProgram(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public List<ProgramState> getProgramStates() {
        return repository.getProgramList();
    }

    public void setProgramList(List<ProgramState> programStateList) {
        repository.setProgramList(programStateList);
    }

    public void addProgram(ProgramState programState) {
        repository.addProgram(programState);
    }
}
