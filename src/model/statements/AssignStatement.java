package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.exceptions.InterpreterException;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;


public class AssignStatement implements IStatement{
    private String id;
    private Expression expression;

    public AssignStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();

        if (symTable.isDefined(id)) {
            Value val = expression.eval(symTable, state.getHeap());
            Type typeID = (symTable.lookup(id)).getType();
            if ((val.getType()).equals(typeID)) {
                symTable.update(id, val);
            }
            else throw new InterpreterException("Declared type of variable " + id + " and type of the assigned expression do not match.");
        }
        else throw new InterpreterException("The used variable " + id + " was not declared before.");

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new AssignStatement(this.id, this.expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = expression.typecheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new InterpreterException("Declared type of variable " + id + " and type of the assigned expression do not match.");
    }

    @Override
    public String toString() {
        return id + '=' + expression.toString();
    }
}
