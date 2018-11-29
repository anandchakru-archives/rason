package rason.app.rest;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rason.app.model.RasonException;
import rason.app.model.FaultResponse;

@RestControllerAdvice
public class RasonControllerAdvice {
	@ExceptionHandler(RasonException.class)
	public @ResponseBody FaultResponse handleIllegalArguement(RasonException ae) {
		return new FaultResponse(ae.getMessage());
	}
	@ExceptionHandler(HttpMessageNotWritableException.class)
	public @ResponseBody FaultResponse handleHttpMessageNotWritableException(HttpMessageNotWritableException hmnwe) {
		return new FaultResponse(hmnwe.getMessage());
	}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public @ResponseBody FaultResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException hmnre) {
		return new FaultResponse(hmnre.getMessage());
	}
}
