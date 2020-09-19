package io.roost.gateway.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.common.exception.CommonException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.roost.gateway.discoverymanager.DiscoveryManager;

@Component
@Order(2)
public class FacadeFilter implements Filter {

	@Autowired
	DiscoveryManager discoveryManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getRequestURI().substring(req.getContextPath().length());
		if (path.startsWith("/service")) {
			try {
				discoveryManager.writeResponse(discoveryManager.loadBalancedRestCall(req, path.substring(8)), res);
			} catch (CommonException e) {
				e.printStackTrace();
				res.setStatus(500);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

}
