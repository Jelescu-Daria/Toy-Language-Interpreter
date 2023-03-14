package model.types;

import model.values.RefValue;
import model.values.Value;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(Type another) {
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        return false;
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deepcopy() {
        return new RefType(inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    public Type getInner() {
        return inner;
    }
}
