package model.statements;

import model.ProgramState;
import model.adts.MyIDictionary;
import model.exceptions.InterpreterException;
import model.types.Type;
import model.values.Value;

public class VarDeclarationStatement implements IStatement {
    private String name;
    private Type type;

    public VarDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(name)) {
            symTable.insert(name, type.defaultValue());
        }
        else throw new InterpreterException("Variable is already declared.");
        return null;
    }

    @Override
    public IStatement deepcopy() {
        return new VarDeclarationStatement(this.name, this.type.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        typeEnv.insert(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
