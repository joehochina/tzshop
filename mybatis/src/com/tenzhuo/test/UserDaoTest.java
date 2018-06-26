package com.tenzhuo.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.tenzhuo.dao.UserDao;
import com.tenzhuo.dao.UserDaoImpl;
import com.tenzhuo.pojo.User;



public class UserDaoTest {

	public SqlSessionFactory sqlSessionFactory;
	public UserDaoImpl userDao;
	@Before
	public void before() throws Exception {
		//���غ��������ļ�
		String resource = "sqlMapConfig.xml";
		InputStream in = Resources.getResourceAsStream(resource);
		//����SqlSessionFactory
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		userDao = new UserDaoImpl(sqlSessionFactory);	
	}
	@Test
	public void testDao() throws Exception {		
		User user = userDao.findUserById(10);
		System.out.println(user);
	}
	
	
	@Test
	public void testInsertUser(){
		User newuser = new User();
		newuser.setAddress("����ʡ�����");
		newuser.setBirthday(new Date());
		newuser.setSex("Ů");
		newuser.setUsername("Сǿ");
		userDao.insertUser(newuser);
	}
	
	@Test
	public void testFindUserByName(){
		List<User> findUserByName = userDao.findUserByName("��");
		for (Iterator iterator = findUserByName.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			System.out.println(user.getId()+" "+user.getUsername());
			
		}
		
	}
	
	@Test
	public void testUpdateUser(){
		User user = new User();
		user.setAddress("����ʡ�����");
		user.setBirthday(new Date());
		user.setSex("��");
		user.setUsername("��Сsgǿ");
		user.setId(1);
		userDao.updateUser(user);
	}
	
	@Test
	public void testDeleteUser(){
		userDao.deleteUser(27);
	}
}
