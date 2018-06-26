package com.tenzhuo.dao;

import java.util.List;

import com.tenzhuo.pojo.User;

public interface UserDao {

	public User findUserById(Integer id);
	public void insertUser(User user);
	public List<User> findUserByName(String username);
	public void updateUser(User user);
	public void deleteUser(Integer id);
}
