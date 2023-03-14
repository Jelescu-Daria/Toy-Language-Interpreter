package model.expressions;

import model.adts.MyIHeap;
import model.exceptions.InterpreterException;
import model.adts.MyIDictionary;
import model.values.Value;
import model.types.Type;

public interface Expression {
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws InterpreterException;
    Expression deepcopy();
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException;
}

