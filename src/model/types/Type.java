package model.types;

import model.values.Value;

public interface Type {
    public boolean equals(Type another);
    public Value defaultValue();
    Type deepcopy();
}
