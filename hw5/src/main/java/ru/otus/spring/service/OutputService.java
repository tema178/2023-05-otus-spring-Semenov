package ru.otus.spring.service;

public interface OutputService {
    void outputString(String s);

    void outputFormatString(String format, Object... args);
}
