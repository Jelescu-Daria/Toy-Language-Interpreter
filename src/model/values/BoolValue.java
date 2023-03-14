package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value {
    private boolean value;

    public BoolValue(boolean val) {
        this.value = val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepcopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Value another) {
        return another instanceof BoolValue;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public boolean getValue() {
        return value;
    }
}
