package service;

import dao.UserDAO;
import dto.request.LoginRequest;
import model.User;
import util.SecurityUtil;

public class AuthService {
	private final UserDAO userDAO = new UserDAO();
	
	public User authenticate(LoginRequest request) {
		String username = request.getUsername();
		String password = request.getPassword();
		
		if(username == null || password == null || username.trim().isEmpty()) {
			return null;
		}
		
		User user = userDAO.findByUsername(username);
		
		if(user == null) {
			return null;
		}
		
		String hashedPassword = user.getHashedPassword();
		boolean isPasswordMatched = SecurityUtil.checkPassword(password, hashedPassword);
		
		// if password is valid, then return user
		if(isPasswordMatched) {
			return user;
		}
		return null; // INVALID password
	}

}
