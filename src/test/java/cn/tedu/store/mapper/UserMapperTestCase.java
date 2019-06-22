package cn.tedu.store.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTestCase {
	
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void addNew() {
		User user = new User();
		user.setUsername("root");
		user.setPassword("1234");
		Integer rows = mapper.addNew(user);
		System.err.println("rows="+rows);
	}

	@Test
	public void updatePassword() {
		Integer uid = 6;
		String password = "rest";
		String modifiedUser = "rest";
		Date modifiedTime = new Date();
		Integer rows = mapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		System.err.println("rows="+rows);
	}
	
	@Test
	public void updateInfo() {
		User user = new User();
		user.setUid(7);
		user.setPhone("2255555");
		user.setEmail("web@qq.com");
		user.setGender(0);
		Integer rows = mapper.updateInfo(user);
		System.err.println(rows);
	}
	
	@Test
	public void updateAvatar() {
		Integer uid = 6;
		String avatar = "1234";
		String modifiedUser = "rest";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		System.err.println("rows="+rows);
	}

	@Test
	public void findByUsername() {
		String username = "digst";
		User user = mapper.findByUsername(username);
		System.err.println(user);
	}

	@Test
	public void findByUid() {
		Integer uid = 5;
		User user = mapper.findByUid(uid);
		System.err.println(user);
	}
	
	
	
	
	
	
	
	
	
	
	
}