package in.kmbs.vlethyme.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginRedirectInterceptor extends HandlerInterceptorAdapter {
	 
	private static final Logger logger = Logger.getLogger(LoginRedirectInterceptor.class);

	private static final String SESSION_VALID = "SESSION_VALID";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		response.setIntHeader("LoginRedirect", 1);
		return true;
	}

	//after the handler is executed
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
		Object handler, ModelAndView modelAndView) throws Exception {
		
		response.setIntHeader("LoginRedirect", 1);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		response.setIntHeader("LoginRedirect", 1);
	}
}
