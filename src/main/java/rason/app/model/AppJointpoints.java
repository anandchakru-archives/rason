package rason.app.model;

import org.aspectj.lang.annotation.Pointcut;

public class AppJointpoints {
	@Pointcut("execution(* rason.app.rest.ApiController.*(..))")
	public void apiController() {
	}
}
