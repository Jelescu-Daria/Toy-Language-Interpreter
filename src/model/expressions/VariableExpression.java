package model.expressions;

import model.adts.MyIHeap;
import model.exceptions.InterpreterException;
import model.adts.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements Expression {
    private String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws InterpreterException {
        return table.lookup(id);
    }

    @Override
    public Expression deepcopy() {
        return new VariableExpression(this.id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
