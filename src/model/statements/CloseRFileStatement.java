package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.exceptions.InterpreterException;
import model.expressions.Expression;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement {
    private Expression exp;

    public CloseRFileStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value expValue = this.exp.eval(state.getSymTable(), state.getHeap());
        if(!expValue.getType().equals(new StringType()))
            throw new InterpreterException("Expression in the Close File statement is not a string.");

        if (!state.getFileTable().isDefined((StringValue) expValue))
            throw new InterpreterException("This file has not been opened.");
        BufferedReader bufferedReader = state.getFileTable().lookup((StringValue) expValue);
        try {
            bufferedReader.close();
            state.getFileTable().remove((StringValue) expValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new CloseRFileStatement(this.exp.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type = exp.typecheck(typeEnv);
        if (type.equals(new StringType())) {
            return typeEnv;
        } else
            throw new InterpreterException("Expression in the Close File statement is not a string.");
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() +
                ')';
    }
}
