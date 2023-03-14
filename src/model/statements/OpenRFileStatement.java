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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStatement implements IStatement {
    Expression exp;

    public OpenRFileStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value expValue = this.exp.eval(state.getSymTable(), state.getHeap());
        if (!expValue.getType().equals(new StringType()))
            throw new InterpreterException("Expression in the Open Read File statement  is not a string.");

        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (fileTable.isDefined(((StringValue) expValue)))
            throw new InterpreterException("This file has already been opened.");

        String filename = ((StringValue) expValue).getValue();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            fileTable.insert((StringValue) expValue, bufferedReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new OpenRFileStatement(this.exp.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type = exp.typecheck(typeEnv);
        if (type.equals(new StringType()))
            return typeEnv;
        else
            throw new InterpreterException("Expression in the Open Read File statement  is not a string.");
    }

    @Override
    public String toString() {
        return "openRFile(" + this.exp.toString() + ")";
    }
}
