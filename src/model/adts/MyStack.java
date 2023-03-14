package model.adts;

import model.exceptions.InterpreterException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() throws InterpreterException {
        if (this.stack.isEmpty())
            throw new InterpreterException("Stack is empty.");
        return this.stack.pop() ;
    }

    @Override
    public void push(T e) {
        this.stack.push(e);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public MyIStack<T> getInstance() {
        return this;
    }

    @Override
    public List<T> getReversed() {
        List<T> l = Arrays.asList((T[])stack.toArray());
        Collections.reverse(l);
        return l;
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public String toString() {
        String string = "";
        List<T> reversed = this.getReversed();
        for (T elem : reversed) {
            string += elem.toString() + "\n";
        }
        return string;
    }
}
