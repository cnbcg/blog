package com.bianchunguang.blog.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/** 
 * @author bianchunguang
 * @version 1.0
 */

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AngularRedirectHomeFilter extends OncePerRequestFilter {

	public static final String HEADER_DNR = "DNR";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
			IOException {

		if (isStaticResouce(request.getRequestURI()) || isRequestByAngular(request)) {
			filterChain.doFilter(request, response);
			
		} else {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			request.getRequestDispatcher("/").forward(request, response);
		}
	}

	private boolean isStaticResouce(String requestURI) {
		return requestURI.endsWith(".css") 
				|| requestURI.endsWith(".js") 
				|| requestURI.endsWith(".gif") 
				|| requestURI.endsWith(".png") 
				|| requestURI.endsWith(".jpeg")
				|| requestURI.endsWith(".jpg")
				|| requestURI.endsWith(".eot")
				|| requestURI.endsWith(".svg")
				|| requestURI.endsWith(".ttf")
				|| requestURI.endsWith(".woff")
				|| requestURI.endsWith(".woff2");
	}

	private boolean isRequestByAngular(HttpServletRequest request) {
		return request.getHeader(HEADER_DNR) != null;
	}

}
