package zerobase.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import zerobase.demo.exception.*;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Exception handleAllException() {
		return new Exception("정의되지 않은 예외 발생");
	}

	@ExceptionHandler(NonExistentUserException.class)
	public NonExistentUserException handleNonExistentUserException() {
		return new NonExistentUserException();
	}

	@ExceptionHandler(NotAuthorizedException.class)
	public NotAuthorizedException handleNotAuthorizedException() {
		return new NotAuthorizedException();
	}
}
