package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value {
    public String getValue() {
        return value;
    }

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepcopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Value another) {
        return another instanceof StringValue;
    }

    @Override
    public String toString() {
        return "\"" + this.value + "\"";
    }
}
