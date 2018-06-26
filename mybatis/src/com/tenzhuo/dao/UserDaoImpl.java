package com.tenzhuo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.tenzhuo.pojo.User;

public class UserDaoImpl implements UserDao {
	
	private SqlSessionFactory sqlSessionFactory;

	public UserDaoImpl(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession openSession = sqlSessionFactory.openSession();
		//test come from User.xml namespace
		return openSession.selectOne("test.findUserById", id);
	
	}
	
	@Override
	public void insertUser(User user) {
		SqlSession openSession = sqlSessionFactory.openSession();
	
		openSession.insert("test.insertUser", user);
		openSession.commit();
		
	}
	
	@Override
	public List<User> findUserByName(String username) {
		SqlSession openSession = sqlSessionFactory.openSession();
		return openSession.selectList("test.findUserByName", username);
	}
	
	@Override
	public void updateUser(User user) {
		SqlSession openSession = sqlSessionFactory.openSession();
		openSession.update("test.updateUser", user);
		openSession.commit();		
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		SqlSession openSession = sqlSessionFactory.openSession();
		openSession.delete("test.deleteUser", id);
		openSession.commit();
		
	}
	
	

}
