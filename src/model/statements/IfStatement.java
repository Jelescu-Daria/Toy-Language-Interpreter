package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.exceptions.InterpreterException;
import model.expressions.Expression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements IStatement {
    private Expression exp;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(Expression exp, IStatement thenStatement, IStatement elseStatement) {
        this.exp = exp;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value condition = exp.eval(state.getSymTable(), state.getHeap());
        if ((condition.getType()).equals(new BoolType())) {
            MyIStack<IStatement> stack = state.getExeStack();
            if (((BoolValue) condition).getValue()) {
                stack.push(thenStatement);
            }
            else {
                stack.push(elseStatement);
            }
        }
        else throw new InterpreterException("Conditional expression is not boolean.");
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new IfStatement(this.exp.deepcopy(), this.thenStatement.deepcopy(), this.elseStatement.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv.deepcopy());
            elseStatement.typecheck(typeEnv.deepcopy());
            return typeEnv;
        } else
            throw new InterpreterException("Conditional expression is not boolean.");
    }

    @Override
    public String toString() {
        return "If " + exp.toString() +
                " Then " + thenStatement.toString() +
                " Else " + elseStatement.toString();
    }
}
