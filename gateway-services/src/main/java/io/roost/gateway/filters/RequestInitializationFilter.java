package io.roost.gateway.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.common.utils.CommonUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RequestInitializationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setAttribute("requestId", CommonUtils.getUUID());
		req.setAttribute("domain", req.getServerName());
		req.setAttribute("requestBy", req.getHeader("requestBy"));
		req.setAttribute("scheme", req.getScheme());
		req.setAttribute("timezone", req.getAttribute("timezone"));
		chain.doFilter(req, response);
	}

}
