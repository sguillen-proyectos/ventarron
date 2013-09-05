package org.inf325.ventarron.services;

public class UserServiceImpl implements UserService {

	@Override
	public boolean isValid(String username, String password) {
		return username.equals("vendedor") && password.equals("12345");
	}
}
