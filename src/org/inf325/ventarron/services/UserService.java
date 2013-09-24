package org.inf325.ventarron.services;

import org.inf325.ventarron.dao.User;

public interface UserService {
	public boolean isValid(String username, String password);
	public User get(String username);
}
