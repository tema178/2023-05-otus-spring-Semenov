package ru.otus.spring.service;

public interface InputService {

    long readLongWithPrompt(String prompt);

    String readStringWithPrompt(String prompt);
}
