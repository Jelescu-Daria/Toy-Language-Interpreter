package model.adts;

import model.exceptions.InterpreterException;

import java.util.HashMap;
import java.util.Set;

public interface MyIDictionary<TKey, TElem> {
    public void insert(TKey k, TElem e);

    public TElem remove(TKey k);

    public TElem lookup(TKey k);

    public void update(TKey k, TElem e) throws InterpreterException;

    public int size();

    public boolean isDefined(TKey k);

    HashMap<TKey, TElem> getContent();

    MyIDictionary<TKey, TElem> deepcopy();

    Set<TKey> getKeySet();
}
