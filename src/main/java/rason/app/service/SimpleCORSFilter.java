package rason.app.service;

import static rason.app.util.RasonConstant.BEAN_CORS_FILTER;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component(value = BEAN_CORS_FILTER)
public class SimpleCORSFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		String origin = ((HttpServletRequest) req).getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
		response.setHeader("Access-Control-Allow-Credentials", "false");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		//response.setHeader("X-Frame-Options", "DENY");
		chain.doFilter(req, res);
	}
	@Override
	public void init(FilterConfig filterConfig) {
	}
	@Override
	public void destroy() {
	}
}