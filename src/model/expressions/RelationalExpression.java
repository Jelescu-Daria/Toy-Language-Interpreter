package model.expressions;

import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.exceptions.InterpreterException;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression{
    private String op;
    private Expression exp1;
    private Expression exp2;

    public RelationalExpression(String op, Expression exp1, Expression exp2) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws InterpreterException {
        Value v1, v2;
        v1 = exp1.eval(table, heap);
        if(!v1.getType().equals(new IntType()))
            throw new InterpreterException("First operand in relational expression must be int.");
        v2 = exp2.eval(table, heap);
        if(!v2.getType().equals(new IntType()))
            throw new InterpreterException("Second operand in relational expression must be int.");

        int n1 = ((IntValue)v1).getValue();
        int n2 = ((IntValue)v2).getValue();

        switch (this.op) {
            case "<":
                return new BoolValue(n1 < n2);
            case "<=":
                return new BoolValue(n1 <= n2);
            case "==":
                return new BoolValue(n1 == n2);
            case "!=":
                return new BoolValue(n1 != n2);
            case ">":
                return new BoolValue(n1 > n2);
            case ">=":
                return new BoolValue(n1 >= n2);
        }

        return null;
    }

    @Override
    public Expression deepcopy() {
        return new RelationalExpression(op, exp1.deepcopy(), exp2.deepcopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type1, type2;
        type1 = exp1.typecheck(typeEnv);
        type2 = exp2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            }
            else
                throw new InterpreterException("Second operand in relational expression must be int.");
        }
        else
            throw new InterpreterException("First operand in relational expression must be int.");
    }

    @Override
    public String toString() {
        return exp1.toString() + op + exp2.toString();
    }
}
