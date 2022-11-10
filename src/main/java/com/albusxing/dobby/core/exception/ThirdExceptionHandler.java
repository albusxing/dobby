package com.albusxing.dobby.core.exception;

import com.albusxing.dobby.common.base.BaseResult;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 *
 * @author liguoqing
 */
@Order(0)
@RestControllerAdvice(basePackages = "com.albusxing.dobby.web.api2")
public class ThirdExceptionHandler {

	@ExceptionHandler(ThirdAuthException.class)
	public BaseResult<?> authException(ThirdAuthException thirdAuthException) {
		return BaseResult.fail(thirdAuthException.getCode(), thirdAuthException.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResult<?> methodArgumentNotValidException(MethodArgumentNotValidException validException) {
		BindingResult result = validException.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		if (!CollectionUtils.isEmpty(fieldErrors)) {
			Set<String> errorSet = fieldErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toSet());
			return BaseResult.fail(errorSet.toString());
		}
		return BaseResult.success();
	}

	/**
	 * 限制约束异常处理
	 * @param violationException
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public BaseResult<?> constraintViolationException(ConstraintViolationException violationException) {
		Set<ConstraintViolation<?>> constraintViolations = violationException.getConstraintViolations();
		if (!CollectionUtils.isEmpty(constraintViolations)) {
			Set<String> errorSet = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
			return BaseResult.fail(errorSet.toString());
		}
		return BaseResult.fail();
	}
}
