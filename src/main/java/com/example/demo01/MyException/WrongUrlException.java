package com.example.demo01.MyException;

public class WrongUrlException extends Exception{
    public WrongUrlException(String message){
        super(message);
    }
}
