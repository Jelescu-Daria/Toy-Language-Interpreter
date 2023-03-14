package model.adts;

import model.exceptions.InterpreterException;
import model.values.Value;

import java.util.HashMap;
import java.util.Map;

public interface MyIHeap<TElem> {
    Integer add(TElem value);
    TElem get(Integer pos) throws InterpreterException;
    boolean containsKey(Integer pos);
    void update(Integer pos, TElem value) throws InterpreterException;
    void setContent(Map<Integer, TElem> newHeap);
    HashMap<Integer, TElem> getContent();
}
