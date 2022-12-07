package zerobase.demo.common.config;

import static zerobase.demo.common.type.ResponseCode.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zerobase.demo.DemoApplication;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.model.BaseResponse;

@Slf4j
@RestControllerAdvice
public class AllExceptionHandler {

	public static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@ExceptionHandler(UserException.class)
	public BaseResponse handleUserException(UserException ex) {
		logger.error("{} is occurred.", ex.getResponseCode());
		return new BaseResponse(ex.getResponseCode());
	}

	@ExceptionHandler(Exception.class)
	public BaseResponse handleAllException(Exception e) {
		log.error("Exception is occurred.", e);

		return new BaseResponse(INTERNAL_SERVER_ERROR);
	}
}
