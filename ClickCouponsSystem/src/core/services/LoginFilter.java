package core.services;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

@WebFilter( urlPatterns   = { "/rest/sec/*" })

public class LoginFilter implements Filter{

	@Context HttpServletRequest req;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
	HttpServletRequest req=(HttpServletRequest) request;
	HttpSession session=req.getSession(false);
	if(session==null){
		System.out.println("no session");
	 }else {
		 chain.doFilter(request, response);
	} 
	 
	}
}














/*
*//**
	 * Servlet Filter implementation class LoginFilter
	 */
/*
 * @WebFilter("/LoginFilter") public class LoginFilter implements Filter {
 * 
 *//**
	 * Default constructor.
	 */
/*
 * public LoginFilter() { // TODO Auto-generated constructor stub }
 * 
 *//**
	 * @see Filter#destroy()
	 */
/*
 * public void destroy() { // TODO Auto-generated method stub }
 * 
 *//**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
/*
 * public void doFilter(ServletRequest request, ServletResponse response,
 * FilterChain chain) throws IOException, ServletException { // TODO
 * Auto-generated method stub // place your code here
 * 
 * // pass the request along the filter chain chain.doFilter(request, response);
 * }
 * 
 *//**
	 * @see Filter#init(FilterConfig)
	 *//*
		 * public void init(FilterConfig fConfig) throws ServletException { // TODO
		 * Auto-generated method stub }
		 * 
		 * }
		 */