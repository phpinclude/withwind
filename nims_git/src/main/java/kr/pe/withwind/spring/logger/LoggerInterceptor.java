package kr.pe.withwind.spring.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoggerInterceptor extends HandlerInterceptorAdapter {
	private static final Log logger = LogFactory.getLog(LoggerInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.debug("afterCompletion called !!");
		logger.debug(" Request URI \t:  " + request.getRequestURI());
		
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		logger.debug("postHandle called !!");
		logger.debug(" Request URI \t:  " + request.getRequestURI());
		
		logger.debug("======================================           END          ======================================\n");
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle called !!");
		logger.debug(" Request URI \t:  " + request.getRequestURI());
		
		if (request.getRequestURI().contains("/resources/")){
			return super.preHandle(request, response, handler);
		}
		
		logger.debug("======================================          START         ======================================");
		logger.debug(" Request URI \t:  " + request.getRequestURI());

		return super.preHandle(request, response, handler);
	}

}
