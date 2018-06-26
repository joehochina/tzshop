package com.tenzhuo.mapper;

import java.util.List;

import com.tenzhuo.pojo.User;

public interface UserMapper {
	
	public User findUserById(Integer id);
	public void insertUser(User user);
	public List<User> findUserByName(String username);
	public void updateUser(User user);
	public void deleteUser(Integer id);

}
