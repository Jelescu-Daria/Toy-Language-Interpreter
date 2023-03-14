package model.expressions;

import model.adts.MyIHeap;
import model.exceptions.InterpreterException;
import model.adts.MyIDictionary;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

import java.util.Objects;

public class LogicExpression implements Expression {
    private Expression e1;
    private Expression e2;
    private String op;

    public LogicExpression(String op, Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws InterpreterException {
        Value v1, v2;
        v1 = e1.eval(table, heap);

        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(table, heap);

            if (v2.getType().equals(new BoolType())) {
                boolean b1 = ((BoolValue)v1).getValue();
                boolean b2 = ((BoolValue)v2).getValue();

                if (Objects.equals(this.op, "and"))
                    return new BoolValue(b1 && b2);
                else if (Objects.equals(this.op, "or"))
                    return new BoolValue(b1 || b2);
            } else
                throw new InterpreterException("Second operand is not a boolean.");
        } else
            throw new InterpreterException("First operand is not a boolean.");

        return null;
    }

    @Override
    public Expression deepcopy() {
        return new LogicExpression(this.op, this.e1.deepcopy(), this.e2.deepcopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new InterpreterException("Second operand is not a boolean.");
        } else
            throw new InterpreterException("First operand is not a boolean.");
    }

    @Override
    public String toString() {
        return "(" + e1.toString() + " " + op + " "  + e2.toString() + ")";
    }
}
