package com.ems.posts.api.controller.exceptions;


public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(){
        super();
    }

    public PostNotFoundException(String message){
        super(message);
    }
}
