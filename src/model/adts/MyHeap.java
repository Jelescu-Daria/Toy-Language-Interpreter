package model.adts;

import model.exceptions.InterpreterException;
import model.values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<TElem> implements MyIHeap<TElem> {
    HashMap<Integer, TElem> heap;
    Integer free;

    public MyHeap(HashMap<Integer, TElem> heap) {
        this.heap = heap;
        free = 1;
    }

    public MyHeap() {
        this.heap = new HashMap<Integer, TElem>();
        free = 1;
    }

    @Override
    public Integer add(TElem value) {
        heap.put(free, value);
        free += 1;
        return free - 1;
    }

    @Override
    public TElem get(Integer pos) throws InterpreterException {
        if (!heap.containsKey(pos))
            throw new InterpreterException("Position " + pos + " does not exist in the heap.");
        return heap.get(pos);
    }

    @Override
    public boolean containsKey(Integer pos) {
        return heap.containsKey(pos);
    }

    @Override
    public void update(Integer pos, TElem value) throws InterpreterException {
        if (!heap.containsKey(pos))
            throw new InterpreterException("Position " + pos + " does not exist in the heap.");
        heap.put(pos, value);
    }

    @Override
    public void setContent(Map<Integer, TElem> newHeap) {
        heap.clear();;
        for (Integer i : newHeap.keySet())
            heap.put(i, newHeap.get(i));
    }

    @Override
    public HashMap<Integer, TElem> getContent() {
        return heap;
    }

    @Override
    public String toString() {
        String str = "";
        for (Integer k : heap.keySet()) {
            str += k.toString() + "->" + heap.get(k).toString() + "\n";
        }
        return str;
    }
}
