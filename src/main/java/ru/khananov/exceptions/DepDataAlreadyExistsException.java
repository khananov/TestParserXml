package ru.khananov.exceptions;

public class DepDataAlreadyExistsException extends RuntimeException {
    public DepDataAlreadyExistsException(String depCode, String depJob) {
        super("DepData with depCode - " + depCode + ", depJob - " + depCode + "already exists.");
    }
}