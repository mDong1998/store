package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserConflictException;
import cn.tedu.store.service.ex.UserNotFoundException;

/**
 * 用户数据的业务层接口
 * @author maggie
 *
 */
public interface IUserService {

	/**
	 * 用户注册
	 * @param user 用户数据
	 * @throws UserConflictException 用户名冲突的异常
	 * @throws InsertException 插入数据失败的异常
	 */
	void reg(User user) throws UserConflictException, InsertException;

	/**
	 * 用户登录
	 * @param username 用户输入的用户名
	 * @param password 用户输入的密码
	 * @return 包含了主要信息的用户对象，有：用户id，用户名
	 * @throws UserNotFoundException
	 * @throws PasswordNotMatchException
	 */
	User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException;

	/**
	 * 修改密码
	 * @param uid 用户id
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws UserNotFoundException
	 * @throws UpdateException
	 * @throws PasswordNotMatchException
	 */
	void changePassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, UpdateException, PasswordNotMatchException;
	
	/**
	 * 修改资料
	 * @param user 用户数据，至少封装用户的uid
	 * @param UpdateException
	 * @param UserNotFoundException
	 */
	void changeInfo(User user) throws UpdateException, UserNotFoundException;
	
	/**
	 * 更新头像
	 * @param uid 用户id
	 * @param avatar 头像的路径
	 * @throws UserNotFoundException
	 * @throws UpdateException
	 */
	void changeAvatar(Integer uid, String avatar) throws UserNotFoundException, UpdateException;

	/**
	 * 根据用户id查询用户信息
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User getByUid(Integer uid);
	
	
	
}