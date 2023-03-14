package model.expressions;

import model.adts.MyIHeap;
import model.exceptions.InterpreterException;
import model.adts.MyIDictionary;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements Expression {
    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws InterpreterException {
        return value;
    }

    @Override
    public Expression deepcopy() {
        return new ValueExpression(this.value.deepcopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
