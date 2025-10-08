package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
	private static final String DEFAULT_REDIRECT = "/books";
	private final String loginPath = "/login";
	private final String logoutPath = "/logout";
	private final String publicResourcePath = "/assets";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String contextPath = req.getContextPath();
		String requestURI = req.getRequestURI();
		
		boolean isLoginPath = requestURI.equals(contextPath + loginPath);
		boolean isLogoutPath = requestURI.equals(contextPath + logoutPath);
		
		boolean isPublicResource = requestURI.startsWith(contextPath + publicResourcePath);
		
		HttpSession session = req.getSession(false);
		
		User user = (session != null) ? (User) session.getAttribute("user") : null;
		boolean isLoggedIn = (user != null);
		
		// If user tries to access public route
		if(isLoginPath || isLogoutPath || isPublicResource) {
			// If user has already logged in and tried to access the login route --> redirect to home page
			if(isLoggedIn && isLoginPath) {
				res.sendRedirect(contextPath + DEFAULT_REDIRECT);
				return;
			}
			
			// Allow user to access public resource or login/logout
			chain.doFilter(request, response);
			return;
		}
		
		// If user tries to access protected route
		if(isLoggedIn) {
			// transition to the next filter
			chain.doFilter(request, response);
		} else {
			// User has not logged in yet --> redirect to login path
			res.sendRedirect(contextPath + loginPath);
		}
	}
}
