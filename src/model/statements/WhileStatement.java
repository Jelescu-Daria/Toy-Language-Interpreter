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

public class WhileStatement implements IStatement {
    private Expression expression;
    private IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        Value expValue = expression.eval(state.getSymTable(), state.getHeap());
        if (!(expValue.getType().equals(new BoolType())))
            throw new InterpreterException("While statement condition is not boolean.");

        MyIStack<IStatement> stack = state.getExeStack();
        if (((BoolValue)expValue).getValue()) {
            stack.push(this);
            stack.push(statement);
        }

        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new WhileStatement(expression, statement);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
       Type typeExp = expression.typecheck(typeEnv);
       if (typeExp.equals(new BoolType())) {
           statement.typecheck(typeEnv.deepcopy());
           return typeEnv;
       } else
           throw new InterpreterException("While statement condition is not boolean.");
    }

    @Override
    public String toString() {
        return "( while(" + expression + ")" + statement + " )";
    }
}
