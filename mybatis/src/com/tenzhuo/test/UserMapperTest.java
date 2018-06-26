package com.tenzhuo.test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.plaf.SeparatorUI;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.tenzhuo.dao.UserDaoImpl;
import com.tenzhuo.mapper.UserMapper;
import com.tenzhuo.pojo.User;



public class UserMapperTest {
	
	public SqlSessionFactory sqlSessionFactory;
	public SqlSession sqlSession;
	public UserMapper userMapper;

	@Before
	public void before() throws Exception {
		//加载核心配置文件
		String resource = "sqlMapConfig.xml";
		InputStream in = Resources.getResourceAsStream(resource);
		//创建SqlSessionFactory
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		sqlSession = sqlSessionFactory.openSession();		
		userMapper = sqlSession.getMapper(UserMapper.class);
		
	}
	@Test
	public void testfindUserById() throws Exception {		
		SqlSession sqlSession = sqlSessionFactory.openSession();		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.findUserById(10);
		System.out.println(user);
	}
	
	
	@Test
	public void testInsertUser(){
	
		User newuser = new User();
		newuser.setAddress("ha湖北省赤壁市");
		newuser.setBirthday(new Date());
		newuser.setSex("女");
		newuser.setUsername("小a强");
		userMapper.insertUser(newuser);
		sqlSession.commit();
		sqlSession.close();
		
	}
	
	@Test
	public void testFindUserByName(){
		List<User> findUserByName = userMapper.findUserByName("小");
		for (Iterator iterator = findUserByName.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			System.out.println(user.getId()+" "+user.getUsername());
			
		}
		
	}
	
	@Test
	public void testUpdateUser(){
		
		User user = new User(); 
		user.setAddress("湖北省赤壁市");
		user.setBirthday(new Date());
		user.setSex("男");
		user.setUsername("张小军");
		user.setId(1);
		userMapper.updateUser(user);
		sqlSession.commit();
		sqlSession.close();
	}
	
	@Test
	public void testDeleteUser(){
		userMapper.deleteUser(7);
		sqlSession.commit();
		sqlSession.close();
	}
}

