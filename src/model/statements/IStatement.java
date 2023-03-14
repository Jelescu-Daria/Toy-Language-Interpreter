package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.exceptions.InterpreterException;
import model.types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws InterpreterException;
    IStatement deepcopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException;
}
