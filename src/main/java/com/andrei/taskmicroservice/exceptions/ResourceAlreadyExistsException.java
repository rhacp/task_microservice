package com.andrei.taskmicroservice.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(String message) { super (message); }
}
