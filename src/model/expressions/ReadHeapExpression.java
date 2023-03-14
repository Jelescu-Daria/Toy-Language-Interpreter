package model.expressions;

import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.exceptions.InterpreterException;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class ReadHeapExpression implements Expression {
    Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws InterpreterException {
        Value exprValue = expression.eval(table, heap);
        if (!(exprValue instanceof RefValue))
            throw new InterpreterException("Read heap expression is not a reference value.");
        return heap.get(((RefValue) exprValue).getAddress());
    }

    @Override
    public Expression deepcopy() {
        return new ReadHeapExpression(expression);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type = expression.typecheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else
            throw new InterpreterException("Read heap expression is not a reference value.");
    }

    @Override
    public String toString() {
        return "rH(" + expression + ")";
    }
}
