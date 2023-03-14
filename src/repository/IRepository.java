package repository;

import model.ProgramState;
import model.exceptions.InterpreterException;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> programStates);
    void logProgramStateExec(ProgramState programState) throws InterpreterException, IOException;
    void addProgram(ProgramState programState);
}
