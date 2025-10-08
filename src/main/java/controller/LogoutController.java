package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {
	private final String loginPath = "/login";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		// Remove the session
		if(session != null) {
			session.invalidate(); // Delete all attributes
		}
		
		// Redirect to /login route
		response.sendRedirect(request.getContextPath() + loginPath);
	}
}
