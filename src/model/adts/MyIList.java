package model.adts;

import model.exceptions.InterpreterException;

import java.util.List;

public interface MyIList<T> {
    public void add(T e);
    public T pop() throws InterpreterException;
    public boolean isEmpty();
    public T getOnPos(int p) throws InterpreterException;
    public int size();
    List<T> getList();
}
