package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIList;
import model.exceptions.InterpreterException;
import model.expressions.Expression;
import model.types.Type;
import model.values.Value;

public class PrintStatement implements IStatement {
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIList<Value> out = state.getOut();
        out.add(expression.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new PrintStatement(this.expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Print(" + expression.toString() + ")";
    }
}
