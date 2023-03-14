package repository;

import model.ProgramState;
import model.exceptions.InterpreterException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private String logFilePath;

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
        this.programStates = new LinkedList<>();
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logProgramStateExec(ProgramState programState) throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.toString());
        logFile.close();
    }

    @Override
    public void addProgram(ProgramState programState) {
        this.programStates.add(programState);
    }
}
