package controller;

import java.io.IOException;

import dto.request.LoginRequest;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.Student;
import model.User;
import service.AuthService;
import service.StudentService;

@WebServlet(urlPatterns = "/login")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {
	AuthService authService = new AuthService();
	StudentService studentService = new StudentService();
	static String DEFAULT_REDIRECT = "/books";
	static String LOGIN_JSP = "/WEB-INF/views/auth/login.jsp";
	String STUDENT_ROLE = "STUDENT"; 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showLoginPage(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		LoginRequest loginRequest = LoginRequest.builder()
				.username(username)
				.password(password)
				.build();
		
		User user = authService.authenticate(loginRequest);
		
		if(user != null) {
			handleSuccessfulLogin(request, response, user);
		} else {
			handleFailedLogin(request, response, username);
		}
	}
	
	private void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		// If user has already login, redirect immediately
		if(session != null && session.getAttribute("user") != null) {
			response.sendRedirect(request.getContextPath() + DEFAULT_REDIRECT);
			return;
		}
		
		// If user has not login yet, then forward to login.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_JSP);
		dispatcher.forward(request, response);
	}
	
	private void handleSuccessfulLogin(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(30 * 60);
		session.setAttribute("user", user);
		
		String userRole = user.getRole();
		if(STUDENT_ROLE.equalsIgnoreCase(userRole)) {
			int id = user.getId();
			Student student = studentService.getStudentProfile(id);
			if(student != null) {
				session.setAttribute("studentProfile", student);
			}
		}
		
		response.sendRedirect(request.getContextPath() + DEFAULT_REDIRECT);
	}
	
	private void handleFailedLogin(HttpServletRequest request, HttpServletResponse response, String username) throws ServletException, IOException {
		request.setAttribute("error", "Error: Username or Password is invalid");
		request.setAttribute("username", username);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_JSP);
		dispatcher.forward(request, response);
	}
}
