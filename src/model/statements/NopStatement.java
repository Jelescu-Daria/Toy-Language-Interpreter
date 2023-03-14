package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.exceptions.InterpreterException;
import model.statements.IStatement;
import model.types.Type;

public class NopStatement implements IStatement {
    public NopStatement() {}

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new NopStatement();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Nop";
    }
}
