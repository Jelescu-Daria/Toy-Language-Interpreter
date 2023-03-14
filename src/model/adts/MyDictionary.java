package model.adts;


import model.exceptions.InterpreterException;

import java.util.HashMap;
import java.util.Set;

public class MyDictionary<TKey, TElem> implements MyIDictionary<TKey, TElem> {
    private HashMap<TKey, TElem> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<TKey, TElem>();
    }

    @Override
    public void insert(TKey k, TElem e) {
        this.dictionary.put(k, e);
    }

    @Override
    public TElem remove(TKey k) {
        return this.dictionary.remove(k);
    }

    @Override
    public TElem lookup(TKey k) {
        return this.dictionary.get(k);
    }

    @Override
    public void update(TKey k, TElem e) throws InterpreterException {
        if (!this.isDefined(k))
            throw new InterpreterException("Cannot update nonexistent key, value pair.");
        this.remove(k);
        this.insert(k, e);
    }

    @Override
    public int size() {
        return this.dictionary.size();
    }

    @Override
    public boolean isDefined(TKey k) {
        return this.dictionary.get(k) != null;
    }

    @Override
    public HashMap<TKey, TElem> getContent() {
        return dictionary;
    }

    @Override
    public MyIDictionary<TKey, TElem> deepcopy() {
        MyIDictionary<TKey, TElem> newDict = new MyDictionary<TKey, TElem>();
        for (TKey key : dictionary.keySet())
            newDict.insert(key, dictionary.get(key));
        return newDict;
    }

    @Override
    public Set<TKey> getKeySet() {
        return dictionary.keySet();
    }

    @Override
    public String toString() {
        String str = "";
        for (TKey k : dictionary.keySet()) {
            str += k.toString() + "->" + dictionary.get(k).toString() + "\n";
        }
        return str;
    }
}
