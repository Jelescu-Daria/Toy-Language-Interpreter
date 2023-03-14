package model.expressions;

import model.adts.MyIHeap;
import model.exceptions.InterpreterException;
import model.adts.MyIDictionary;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression {
    private Expression e1;
    private Expression e2;
    private String op;

    public ArithmeticExpression(String op, Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws InterpreterException {
        Value v1, v2;
        v1 = e1.eval(table, heap);

        if(v1.getType().equals(new IntType())) {
            v2 = e2.eval(table, heap);

            if (v2.getType().equals(new IntType())) {
                int n1 = ((IntValue)v1).getValue();
                int n2 = ((IntValue)v2).getValue();

                switch (this.op) {
                    case "+":
                        return new IntValue(n1 + n2);
                    case "-":
                        return new IntValue(n1 - n2);
                    case "*":
                        return new IntValue(n1 * n2);
                    case "/":
                        if (n2 == 0)
                            throw new InterpreterException("Division by zero.");
                        return new IntValue(n1 / n2);
                }
            } else
                throw new InterpreterException("Second operand is not an integer.");
        } else
            throw new InterpreterException("First operand is not an integer.");
        return null;
    }

    @Override
    public Expression deepcopy() {
        return new ArithmeticExpression(this.op, this.e1.deepcopy(), this.e2.deepcopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type1.equals(new IntType())) {
                return new IntType();
            } else
                throw new InterpreterException("Second operand is not an integer.");
        } else
            throw new InterpreterException("First operand is not an integer.");
    }

    @Override
    public String toString() {
        return e1.toString() + op + e2.toString();
    }
}
