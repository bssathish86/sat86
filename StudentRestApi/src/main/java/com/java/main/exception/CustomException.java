package com.java.main.exception;

public class CustomException
{

    private final String errorMessage;

    public CustomException(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

}
