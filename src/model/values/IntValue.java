package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value {
    private int value;

    public IntValue(int val) {
        this.value = val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepcopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Value another) {
        return another instanceof IntValue;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public int getValue() {
        return value;
    }
}
