package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTestCase {

	@Autowired
	IUserService userService;

	@Test
	public void reg() {
		try {
			User user = new User();
			user.setUsername("md5");
			user.setPassword("3234");
			user.setPhone("1344326363");
			user.setEmail("md5@qq.com");
			user.setGender(0);
			user.setAvatar("http://www.tedu.cn/logo.png");
			userService.reg(user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void login() {
		try {
			String username = "md5";
			String password = "3234";
			User user = userService.login(username, password);
			System.err.println(user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changePassword() {
		try {
			Integer uid = 2;
			String oldPassword = "web";
			String newPassword = "wweebb";
			userService.changePassword(uid, oldPassword, newPassword);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeInfo() {
		try {
			User user = new User();
			user.setUid(6);
			user.setPhone("222");
			user.setEmail("xzx@qq.com");
			user.setGender(1);
			userService.changeInfo(user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeAvatar() {
		try {
			Integer uid = 6;
			String avatar = "lalala";
			userService.changeAvatar(uid, avatar);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void getByUid() {
		Integer uid = 6;
		User user = userService.getByUid(uid);
		System.err.println(user);
	}
	
	

}