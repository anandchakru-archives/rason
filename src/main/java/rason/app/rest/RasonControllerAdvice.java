package rason.app.rest;

import javax.servlet.ServletException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rason.app.model.FaultResponse;
import rason.app.model.RasonException;

@RestControllerAdvice
public class RasonControllerAdvice {
	/**
	 * RasonException
	 * 
	 * @param ae
	 * @return
	 */
	@ExceptionHandler(RasonException.class)
	public @ResponseBody FaultResponse handleRasonException(RasonException ae) {
		return new FaultResponse(ae.getMessage());
	}
	/**
	 * 
	 * HttpMessageNotWritableException
	 * HttpMessageNotReadableException
	 * 
	 * @param hmce
	 * @return
	 */
	@ExceptionHandler(HttpMessageConversionException.class)
	public @ResponseBody FaultResponse handleHttpMessageConversionException(HttpMessageConversionException hmce) {
		return new FaultResponse(hmce.getMessage());
	}
	/**
	 * 
	 * HttpMediaTypeException
	 * HttpSessionRequiredException
	 * ModelAndViewDefiningException
	 * MissingServletRequestPartException
	 * HttpRequestMethodNotSupportedException
	 * NestedServletException
	 * NoHandlerFoundException
	 * UnavailableException
	 * 
	 * @param se
	 * @return
	 */
	@ExceptionHandler(ServletException.class)
	public @ResponseBody FaultResponse handleServletException(ServletException se) {
		return new FaultResponse(se.getMessage());
	}
}
