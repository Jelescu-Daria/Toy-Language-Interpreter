package model.adts;

import model.exceptions.InterpreterException;

import java.util.List;
import java.util.Vector;

public class MyList<T> implements MyIList<T>{
    private Vector<T> list;

    public MyList() {
        this.list = new Vector<T>();
    }

    @Override
    public void add(T e) {
        this.list.add(e);
    }

    @Override
    public T pop() throws InterpreterException {
        if (this.list.isEmpty())
            throw new InterpreterException("List is empty.");
        return this.list.get(0);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public T getOnPos(int p) throws InterpreterException {
        if (this.list.size() < p)
            throw new InterpreterException("Position is out of bounds.");
        return this.list.get(p);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public List<T> getList() {
        return this.list;
    }


    @Override
    public String toString() {
        String string = "";
        for (T elem : this.list) {
            string += elem.toString() + "\n";
        }
        return string;
    }
}
