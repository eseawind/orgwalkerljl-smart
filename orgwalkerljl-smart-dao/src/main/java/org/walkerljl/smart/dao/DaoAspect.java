package org.walkerljl.smart.dao;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.walkerljl.commons.exception.AppDaoException;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;

/**
 * 数据访问层切面
 * 
 * @author lijunlin
 */
//@Aspect
//@Component("daoAspect")
public class DaoAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DaoAspect.class);
	
	/** 声明切点*/
	@Pointcut("execution(public * org.walkerljl.smart.dao..*.*(..))")
	public void actionMethod(){}
	
	/** 环绕增强*/
	@Around("actionMethod()")
	public Object doAspect(ProceedingJoinPoint joinPoint) {
		long startTime = System.currentTimeMillis();
		Object returnValue = null;
		boolean invokedResult = true;
		try {
			Object[] args = joinPoint.getArgs();
			returnValue = joinPoint.proceed(args);
		} catch (Throwable e) {
			invokedResult = false;
			throw new AppDaoException(e.getMessage(), e);
		} finally {
			//DEBUG
			if (LOGGER.isDebugEnabled()) {
				long cousumeTime = System.currentTimeMillis()-startTime;
				String clazzName = joinPoint.getTarget().getClass().getName();
				String invokedMethodName = joinPoint.getSignature().getName();
				String debugInfo = clazzName + "." + invokedMethodName;
				LOGGER.debug(String.format("方法[%s]此次调用共耗时:%s毫秒, 结果:%s.", 
						new Object[]{debugInfo, cousumeTime, (invokedResult ? "成功" : "失败")}));
			}
		}
		return returnValue;
	}
}