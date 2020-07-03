package com.topics.knowledgeBase.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static java.time.LocalDateTime.now;

@ControllerAdvice
public class KnowledgeAppExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        CustomExceptionBean customExceptionBean = new CustomExceptionBean(ZonedDateTime.of(now(), ZoneId.of("UTC")).toString(), "Method argument not valid", ex.getBindingResult().getFieldError().getField());
        return new ResponseEntity<Object>(customExceptionBean, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        CustomExceptionBean customExceptionBean = new CustomExceptionBean(ZonedDateTime.of(now(), ZoneId.of("UTC")).toString(), "Request Method not valid", ex.getMessage());
        return new ResponseEntity<Object>(customExceptionBean, HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TopicNameNotUniqueException.class)
    @ResponseBody
    public final CustomExceptionBean handleTopicNameNotUniqueException(TopicNameNotUniqueException ex) {
        return new CustomExceptionBean(ZonedDateTime.of(now(), ZoneId.of("UTC")).toString(), ex.getMessage(), ex.getTopicName());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SubTopicNameNotUniqueException.class)
    @ResponseBody
    public final CustomExceptionBean handleSubTopicNameNotUniqueException(SubTopicNameNotUniqueException ex) {
        return new CustomExceptionBean(ZonedDateTime.of(now(), ZoneId.of("UTC")).toString(), ex.getMessage(), ex.getSubTopicName());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TopicIdNotFoundException.class)
    @ResponseBody
    public final CustomExceptionBean handleTopicIdNotFoundException(TopicIdNotFoundException ex) {
        return new CustomExceptionBean(ZonedDateTime.of(now(), ZoneId.of("UTC")).toString(), ex.getMessage(), ex.getTopicId().toString());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TopicNameNotFoundException.class)
    @ResponseBody
    public final CustomExceptionBean handleTopicNameNotFoundException(TopicNameNotFoundException ex, HttpServletRequest request) {
        return new CustomExceptionBean(ZonedDateTime.of(now(), ZoneId.of("UTC")).toString(), ex.getMessage(), ex.getTopicName()+" ----- "+request.getRequestedSessionId());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public final CustomExceptionBean handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        return new CustomExceptionBean(ZonedDateTime.of(now(), ZoneId.of("UTC")).toString(), ex.getMessage(), request.getRequestedSessionId());
    }
}
