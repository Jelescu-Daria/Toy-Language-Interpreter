package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.exceptions.InterpreterException;
import model.expressions.Expression;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private Expression exp;
    private String varName;

    public ReadFileStatement(Expression exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        if (!state.getSymTable().isDefined(varName))
            throw new InterpreterException("Variable in the Read File statement is not defined.");
        if (!state.getSymTable().lookup(varName).getType().equals(new IntType()))
            throw new InterpreterException("Variable in the Read File statement is not int.");

        Value expValue = this.exp.eval(state.getSymTable(), state.getHeap());
        if(!expValue.getType().equals(new StringType()))
            throw new InterpreterException("Expression in the Read File statement is not a string.");

        if(!state.getFileTable().isDefined((StringValue) expValue))
            throw new InterpreterException("This file has not been opened.");
        BufferedReader bufferedReader = state.getFileTable().lookup((StringValue) expValue);
        String line;
        try {
            line = bufferedReader.readLine();
            if (line == null)
                line = "0";
            int lineInt = Integer.parseInt(line);
            state.getSymTable().insert(varName, new IntValue(lineInt));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new ReadFileStatement(exp.deepcopy(), varName);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeExp = exp.typecheck(typeEnv);
        Type typeVar = typeEnv.lookup(varName);
        if (typeExp.equals(new StringType())) {
            if (typeVar.equals(new IntType())) {
                return typeEnv;
            } else
                throw new InterpreterException("Variable in the Read File statement is not int.");
        } else
            throw new InterpreterException("Expression in the Read File statement is not a string.");
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() +
                "," + varName + ")";
    }
}
