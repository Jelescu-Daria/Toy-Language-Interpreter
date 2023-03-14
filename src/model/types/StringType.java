package model.types;

import model.values.StringValue;
import model.values.Value;

public class StringType implements Type{
    @Override
    public boolean equals(Type another) {
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepcopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }
}
