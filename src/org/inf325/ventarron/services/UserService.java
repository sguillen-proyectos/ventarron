package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.User;

public interface UserService {
	public List<User> getUsers();
	public User get(String username, String password);
	public User get(String username);
	public User get(int id);
	public void insert(User user);
	public void update(User user);
	public void delete(User user);
}
