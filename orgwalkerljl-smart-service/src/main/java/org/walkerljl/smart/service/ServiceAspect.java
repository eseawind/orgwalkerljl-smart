package org.walkerljl.smart.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.walkerljl.commons.exception.AppServiceException;
import org.walkerljl.commons.util.IntegerUtils;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;

/**
 * 业务逻辑层切面
 * 
 * @author lijunlin
 */
//@Aspect
//@Component("serviceAspect")
public class ServiceAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);
	
	//@Resource private SysLogService sysLogService;
	
	/** 声明切点*/
	@Pointcut("execution(public * org.walkerljl.smart.service..*.*(..))")
	public void actionMethod(){}
	
	/** 环绕增强*/
	@Around("actionMethod()")
	public Object doAspect(ProceedingJoinPoint joinPoint) {
		long startTime = System.currentTimeMillis();
		Object returnValue = null;
		boolean invokedResult = true;
		boolean sysLogResult = true;
		String errMsg = "";
		Object[] args = joinPoint.getArgs();
		try {
			returnValue = joinPoint.proceed(args);
			if (returnValue != null) {
				if (returnValue.getClass() == Boolean.class) {
					sysLogResult = Boolean.valueOf(returnValue.toString());
				} else if (returnValue.getClass() == Integer.class) {
					sysLogResult = IntegerUtils.greatThanEquals(Integer.parseInt(returnValue.toString()), 0);
				} 
			}
		} catch (Throwable e) {
			invokedResult = false;
			errMsg = e.getMessage();
			throw new AppServiceException(e.getMessage(), e);
		} finally {
			long cousumeTime = System.currentTimeMillis() - startTime;
			processSysLog(joinPoint.getTarget(), args, sysLogResult, cousumeTime, errMsg);
			
			//DEBUG
			if (LOGGER.isDebugEnabled()) {
				String clazzName = joinPoint.getTarget().getClass().getName();
				String invokedMethodName = joinPoint.getSignature().getName();
				String debugInfo = clazzName + "." + invokedMethodName;
				LOGGER.debug(String.format("方法[{}]此次调用共耗时:{}毫秒, 结果:{}.", 
						new Object[]{debugInfo, cousumeTime, (invokedResult ? "成功" : "失败")}));
			}
		}
		return returnValue;
	}
	
	/**
	 * 处理系统日志
	 * @param target
	 * @param args
	 * @param sysLogResult
	 * @param consumeTime
	 * @param errMsg
	 */
	private void processSysLog(final Object target,final Object[] args, final boolean sysLogResult, 
			final long consumeTime, final String errMsg) {
//		if (target != null && ArrayUtils.isNotEmpty(args) && !(target instanceof SysLogService)) {
//			ThreadPool.execute(new Runnable() {
//				@Override
//				public void run() {
//					for (Object arg : args) {
//						if (arg != null && (arg instanceof SysLog) ) {
//							SysLog sysLog = (SysLog) arg;
//							if (StringUtils.isNotBlank(sysLog.getUserId()) 
//									&& StringUtils.isNotBlank(sysLog.getUserName())
//									&& sysLog.getSysLogModule() != null
//									&& sysLog.getOperateType() != null) {
//								
//								if (StringUtils.isNotBlank(errMsg)) {
//									sysLog.setMessage(sysLog.getMessage() + ",serviceAspect错误信息:" + errMsg);
//								}
//								sysLog.setRemark(sysLog.getRemark() + "cs:" + consumeTime);
//								if (sysLog.getResult() == null) {
//									sysLog.setResult(sysLogResult);	
//								}
//								sysLogService.log(sysLog);
//								if (LOG.isDebugEnabled()) {
//									LOG.debug("记录系统操作日志[{}]", sysLog.toString());
//								}
//							}
//						}
//					}
//				}
//			});
//		}
	}
}