package model.values;

import model.types.RefType;
import model.types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepcopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public boolean equals(Value another) {
        return another instanceof RefValue;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public String toString() {
        return "(" + address + "," + locationType + ")";
    }
}
