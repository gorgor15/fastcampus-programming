package com.fastcampus.programming.dmaker.exception;

import com.fastcampus.programming.dmaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(DMakerException e, HttpServletRequest request){
        log.error("errorCode:{}, url: {}, message:{}",e.getDMakerErrorCode(),request.getRequestURL(),e.getDetailMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            //POST요청을 보내야되는데 다른 요청을 보냈을때 나타남
            HttpRequestMethodNotSupportedException.class,
            //Request에 NotNull이런걸하고있는데 문제가 발생하면 아래께 동작(Notnull인데 null일떄 나타나는 오류)
            MethodArgumentNotValidException.class
    })
    public DMakerErrorResponse handleBadRequest(
            Exception e, HttpServletRequest request
    ){
        log.error("url: {}, message:{}",request.getRequestURL(),e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INVALID_REQUEST)
                .errorMessage(DMakerErrorCode.INVALID_REQUEST.getMessage())
                .build();
    }


    @ExceptionHandler(Exception.class)
    public DMakerErrorResponse handleException(
            Exception e, HttpServletRequest request
    ){
        log.error("url: {}, message:{}",request.getRequestURL(),e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INTERNAL_SERVER_ERROR)
                .errorMessage(DMakerErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
