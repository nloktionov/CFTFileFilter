package ru.nloktionov.type;

public class TypeResolver {
    public Type resolve(String line) {
        if (line.matches("-?\\d+")) {
            return Type.INTEGER;
        } else if (line.matches("-?\\d*(\\.\\d+)?([eE][-+]?\\d+)?")) {
            return Type.FLOAT;
        } else {
            return Type.STRING;
        }
    }
}
