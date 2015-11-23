package org.walkerljl.smart.mvc;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.walkerljl.log.Logger;
import org.walkerljl.log.LoggerFactory;
import org.walkerljl.smart.mvc.util.JSONUtils;

/**
 * 启动类
 *
 * @author lijunlin
 */
@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
public class Bootstrap {
	
	private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);
	
	private boolean isRunning = false;
		
	/**
	 * 初始化资源
	 */
	@PostConstruct
	public void init() {
		synchronized (this) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Init resource finished");
			}
			isRunning = true;
		}
	}
	
	/**
	 * 释放资源
	 */
	@PreDestroy
	public void destory() {
		synchronized (this) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Destory resource finished");
			}
			isRunning = false;
		}
	}
	
	/**
	 * 状态检测
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/status")
	public ModelAndView status(HttpServletResponse response) throws IOException {
		JSONUtils.write(response.getWriter(), (isRunning ? "Running..." : "Stopped..."), null);
		return null;
	}
}