package rason.app.service;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import rason.app.model.RasonException;

@Aspect
@Service
public class ApiControllerValidationService {
	@Around("rason.app.model.AppJointpoints.apiController()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		if (args == null || args.length < 1) {
			throw new RasonException("missing bucket.");
		}
		CharSequence bucketKey = (CharSequence) args[0];
		if (StringUtils.isEmpty(bucketKey) || StringUtils.length(bucketKey) < 3 || StringUtils.length(bucketKey) > 10) {
			throw new RasonException("invalid bucket. min-len:3 & max-len:10");
		}
		Object ret = null;
		try {
			ret = joinPoint.proceed();
		} catch (Exception e) {
			throw e;
		}
		return ret;
	}
}