package model;

import model.adts.*;
import model.exceptions.InterpreterException;
import model.statements.IStatement;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Value> heap;
    private IStatement originalProgram;
    public Integer id;
    private static List<Integer> idList = new ArrayList<>();

    public MyIHeap<Value>  getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap<Value>  heap) {
        this.heap = heap;
    }

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Value>  heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = newId();
    }

    public ProgramState(IStatement originalProgram) {
        this.exeStack = new MyStack<>();
        this.symTable = new MyDictionary<>();
        this.out = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heap = new MyHeap<>();
        this.exeStack.push(originalProgram);
        this.id = newId();
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIList<String> getOutAsString() {
        List<Value> outList = out.getList();
        MyIList<String> outString = new MyList<>();
        for (Value value : outList)
            outString.add(value.toString());
        return outString;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "------------" +
                "\nID: " + id + "\n" +
                "\nExecution stack:\n" + exeStack.toString() +
                "\nSymbol table:\n" + symTable.toString() +
                "\nOutput:\n" + out.toString() +
                "\nFile table:\n" + fileTable.toString() +
                "\nHeap:\n" + heap.toString() +
                "\n";
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public ProgramState oneStep() throws InterpreterException {
        if (exeStack.isEmpty())
            throw new InterpreterException("Program state stack is empty.");
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    private static Integer newId() {
        Random random = new Random();
        int newId;
        synchronized (idList) {
            do {
                newId = random.nextInt(100);
            } while (idList.contains(newId));
            idList.add(newId);
        }
        return newId;
    }
}
