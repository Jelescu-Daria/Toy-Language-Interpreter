package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.exceptions.InterpreterException;
import model.expressions.Expression;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class WriteHeapStatement implements IStatement {
    private String varName;
    private Expression expression;

    public WriteHeapStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new InterpreterException("Variable " + varName + " is not defined.");

        Value varValue = symTable.lookup(varName);
        if (!(varValue.getType() instanceof RefType))
            throw new InterpreterException("Variable " + varName + " is not of reference type.");

        Integer heapPos = ((RefValue)varValue).getAddress();
        if (!state.getHeap().containsKey(heapPos))
            throw new InterpreterException("The heap address of variable " + varName + " is not valid.");

        Value exprValue = expression.eval(symTable, state.getHeap());
        Type locationType = ((RefValue) varValue).getLocationType();
        if (! (exprValue.getType().equals(locationType)))
            throw new InterpreterException("The type of the variable " + varName + " is not the same as the given expression.");

        state.getHeap().update(heapPos, exprValue);

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new WriteHeapStatement(varName, expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeExp = expression.typecheck(typeEnv);
        Type typeVar = typeEnv.lookup(varName);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else
            throw new InterpreterException("The type of the variable " + varName + " is not the same as the given expression.");
    }

    @Override
    public String toString() {
        return "wH("+ varName + ',' + expression + ')';
    }
}
