package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.adts.MyStack;
import model.exceptions.InterpreterException;
import model.types.Type;



public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIStack<IStatement> newExeStack = new MyStack<>();
        newExeStack.push(statement);
        return new ProgramState(newExeStack, state.getSymTable().deepcopy(),
                state.getOut(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IStatement deepcopy() {
        return new ForkStatement(statement.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        statement.typecheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement + ')';
    }
}
