package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.exceptions.InterpreterException;
import model.types.Type;

public class CompStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIStack<IStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new CompStatement(this.first.deepcopy(), this.second.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ')';
        //return String.format("%s;\n%s", first, second);
    }
}
