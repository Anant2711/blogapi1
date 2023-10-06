package com.blogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)//when exception occurs it will give us status not found(404 not found)
public class ResourceNotFoundException extends RuntimeException {//when you are building custom exception we were making it extends throwable but here extend runtime exception because  i want this exception purely runtime not compile time
public ResourceNotFoundException(long id){
 super("Resource not found for id:"+id);   //when i create a object resourcenotfound it will this long id and supply id number to super key does resource not found with id number in the response of your postman
}
}
