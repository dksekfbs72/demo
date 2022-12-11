package zerobase.demo.common.config;

import static zerobase.demo.common.type.ResponseCode.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zerobase.demo.DemoApplication;
import zerobase.demo.common.exception.OwnerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class AllExceptionHandler {

	public static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@ExceptionHandler(UserException.class)
	public ErrorResponse handleUserException(UserException ex) {
		logger.error("{} is occurred.", ex.getResponseCode());
		return new ErrorResponse(ex.getResponseCode());
	}

	@ExceptionHandler(OwnerException.class)
	public ErrorResponse handleOwnerException(OwnerException ex) {
		logger.error("{} is occurred.", ex.getMessage());
		return new ErrorResponse(ex.getResponseCode());
	}

	@ExceptionHandler(Exception.class)
	public ErrorResponse handleAllException(Exception e) {
		log.error("Exception is occurred.", e);

		return new ErrorResponse(INTERNAL_SERVER_ERROR);
	}
}
