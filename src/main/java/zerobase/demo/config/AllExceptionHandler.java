package zerobase.demo.config;

import static zerobase.demo.type.ResponseCode.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zerobase.demo.DemoApplication;
import zerobase.demo.exception.UserException;
import zerobase.demo.model.ResponseResult;

@Slf4j
@RestControllerAdvice
public class AllExceptionHandler {

	public static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@ExceptionHandler(UserException.class)
	//모든 오류를 핸들링
	public ResponseResult handleUserException(UserException ex) {
		logger.error("{} is occurred.", ex.getResponseCode());
		return new ResponseResult(ex.getResponseCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseResult handleAllException(Exception e) {
		log.error("Exception is occurred.", e);

		return new ResponseResult(INTERNAL_SERVER_ERROR);
	}
}
