package model.adts;

import model.exceptions.InterpreterException;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T> {
    public T pop() throws InterpreterException;
    public void push(T e);
    public boolean isEmpty();
    public int size();
    public MyIStack<T> getInstance();
    List<T> getReversed();
    Stack<T> getStack();
}
