package tflow.com.yzs.flow.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        //HttpServletResponse httpResponse = (HttpServletResponse) response;
		logger.info("2uri:" + httpRequest.getRequestURI());
		logger.info("2url:" + httpRequest.getRequestURL());
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}

}
