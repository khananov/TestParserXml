package ru.khananov.exceptions;

public class DepDataNotFoundException extends RuntimeException {
    public DepDataNotFoundException(Long id) {
        super("DepData with id - " + id + " not found.");
    }
}
