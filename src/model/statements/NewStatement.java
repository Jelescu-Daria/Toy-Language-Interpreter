package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.exceptions.InterpreterException;
import model.expressions.Expression;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class NewStatement implements IStatement {
    private String varName;
    private Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new InterpreterException("The variable " + varName + " is not defined.");

        Value varValue = symTable.lookup(varName);
        if(!(varValue.getType() instanceof RefType))
            throw new InterpreterException("The variable " + varName + " is not a reference type.");

        Value exprValue = expression.eval(symTable, state.getHeap());
        Type locationType = ((RefValue)varValue).getLocationType();
        if (!locationType.equals(exprValue.getType()))
            throw new InterpreterException("The type of the variable " + varName + " is not the same as the type of the given expression.");

        Integer heapPos = state.getHeap().add(exprValue);
        symTable.insert(varName, new RefValue(heapPos, locationType));

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new NewStatement(this.varName, this.expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = expression.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        }
        else
            throw new InterpreterException("The type of the variable " + varName + " is not the same as the type of the given expression.");
    }

    @Override
    public String toString() {
        return "New(" + varName + ',' + expression + ')';
    }
}
