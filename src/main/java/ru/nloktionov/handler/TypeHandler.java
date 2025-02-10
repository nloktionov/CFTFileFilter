package ru.nloktionov.handler;

public interface TypeHandler {
    void handle(String line);

    void close();
}
