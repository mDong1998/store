package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserConflictException;
import cn.tedu.store.service.ex.UserNotFoundException;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void reg(User user) throws UserConflictException, InsertException {
		//根据user.getUsername()获取用户名匹配的数据
		String username = user.getUsername();
		//检查数据是否为null
		User data = findByUsername(username);
		if(data == null) {
			//是，为null，用户名未被占用，则应该补全参数中的属性值
			// 1.密码加密
			String salt = UUID.randomUUID().toString().toUpperCase();
			String md5Password = getMd5Password(user.getPassword(), salt);
			user.setPassword(md5Password);
			// 2.封装salt
			user.setSalt(salt);
			//3.封装isDelete，固定为0
			user.setIsDelete(0);
			//4.封装4项日志数据
			Date now = new Date();
			user.setCreatedTime(now);
			user.setModifiedTime(now);
			user.setCreatedUser(username);
			user.setModifiedUser(username);
			//执行注册：addNew(user)
			addNew(user);
		} else {
			//否，非null，用户名已被占用，抛出UserConflictException
			throw new UserConflictException("你尝试注册的用户名（"+username+"）已被占用");
		}
	}

	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		//根据username查询用户数据，判断用户数据是否为null
		User user = userMapper.findByUsername(username);
		if(user == null) {
			//是：为null，即用户数据不存在，则抛出UserNotFoundException
			throw new UserNotFoundException("你尝试登录的账号（"+username+"）不存在");
		} else {
			//否，非null，即用户数据存在，判断是否已删除：isDelete.equals(1)
			if(user.getIsDelete().equals(1)) {
				//是：已删除，则抛出UserNotFoundException
				throw new UserNotFoundException("你尝试登录的账号（"+username+"）用户数据已被删除");
			} else {
				//否，未删除，先取出salt，将参数password结合salt执行加密，判断密码是否匹配
				String md5Password = getMd5Password(password, user.getSalt());
				if(user.getPassword().contentEquals(md5Password)) {
					//是：先将查询到的用户数据中的password和salt属性设置为null，再返回
					user.setPassword(null);
					user.setSalt(null);
					return user;
				} else {
					//否：抛出PasswordNotMatchException
					throw new PasswordNotMatchException("密码错误");
				}
			}
		}
	}

	@Override
	public void changePassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, UpdateException, PasswordNotMatchException {
		//根据uid查询用户信息
		User user = findByUid(uid);
		//判断查询结果是否为null
		if(user == null) {
			//是：用户数据不存在，抛出UserNotFoundException
			throw new UserNotFoundException("修改密码失败！用户不存在");
		} else {
			//判断isDelete是否为1
			if(user.getIsDelete().equals(1)) {
				//是：用户已被标记为删除，抛出UserNotFoundException
				throw new UserNotFoundException("用户已被删除");
			} else {
				//从查询结果中取出盐值salt，将oldPassword和salt进行加密，得到oldMd5Password
				String salt = user.getSalt();
				String oldMd5Password = getMd5Password(oldPassword, salt);
				//判断oldMd5Password和查询到的密码是否匹配
				if(oldMd5Password.equals(user.getPassword())) {
					//是，原密码正确，将newPassword和salt进行加密，得到newMd5Password
					String newMd5Password = getMd5Password(newPassword, salt);
					Date now = new Date();
					updatePassword(uid, newMd5Password, user.getUsername(), now);
				} else {
					//否，原密码错误，抛出PasswordNotMatchException
					throw new PasswordNotMatchException("原密码不正确");
				}
			}
		}
	}

	@Override
	public void changeInfo(User user) {
		//通过user.getUid()查询用户信息
		Integer uid = user.getUid();
		User data = userMapper.findByUid(uid);
		//判断查询结果是否为null
		if(data == null) {
			//是，抛出UserNotFoundException
			throw new UserNotFoundException("用户不存在");
		}
		//判断查询结果中的isDelete是否为1
		if(data.getIsDelete().equals(1)) {
			//是，用户已被删除，抛出UserNotFoundException
			throw new UserNotFoundException("用户已被删除");
		}
		//在user中封装modified_user
		user.setModifiedUser(data.getUsername());
		user.setModifiedTime(new Date());
		updateInfo(user);
	}

	@Override
	public void changeAvatar(Integer uid, String avatar) throws UserNotFoundException, UpdateException {
		//通过user.getUid()查询用户信息
		User user = userMapper.findByUid(uid);
		//判断查询结果是否为null
		if(user == null) {
			//是，抛出UserNotFoundException
			throw new UserNotFoundException("用户不存在");
		}
		//判断查询结果中的isDelete是否为1
		if(user.getIsDelete().equals(1)) {
			//是，用户已被删除，抛出UserNotFoundException
			throw new UserNotFoundException("用户已被删除");
		}
		//从当前结果中获取用户名
		String modifiedUser = user.getUsername();
		//创建当前时间对象
		Date modifiedTime = new Date();
		//更新头像
		updateAvatar(uid, avatar, modifiedUser, modifiedTime);
	}

	@Override
	public User getByUid(Integer uid) {
		//查询数据
		User user = findByUid(uid);
		//判断用户数据是否存在
		if(user == null) {
			throw new UserNotFoundException("用户不存在!");
		}
		//判断用户数据是否已被删除
		if(user.getIsDelete().equals(1)) {
			throw new UserNotFoundException("用户不存在!");
		}
		//清除不希望对外暴露的数据
		user.setPassword(null);
		user.setSalt(null);
		user.setIsDelete(null);
		return user;
	}

	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	private void addNew(User user) {
		Integer rows = userMapper.addNew(user);
		if(rows != 1) {
			throw new InsertException("增加用户数据时出现未知错误！请联系系统管理员");
		} 
	}

	/**
	 * 使用MD5算法执行密码加密
	 * @param password 密码原文
	 * @param salt 盐值
	 * @return 加密后的密文 //蜜钥(miyue)
	 */
	private String getMd5Password(String password, String salt) {
		//加密规则
		//1.将盐值添加到原文的左侧
		//2.执行加密10次
		String str = salt + password;
		for (int i = 0; i < 10; i++) {
			str = DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
		}
		return str;
	}
	
	/**
	 * 更新用户的密码
	 * @param uid
	 * @param password
	 * @param modifiedUser
	 * @param modifiedTime
	 */
	private void updatePassword(Integer uid, String password, String modifiedUser,
			Date modifiedTime) {
		Integer rows = userMapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		if(rows != 1) {
			throw new UpdateException("更新密码时出现未知错误");
		}
	}
	
	/**
	 * 修改用户资料
	 * @param user 用户数据
	 */
	private void updateInfo(User user) {
		Integer rows = userMapper.updateInfo(user);
		if(rows != 1) {
			throw new UpdateException("更新用户资料时出现未知错误");
		}
	}
	
	/**
	 * 更新头像
	 * @param uid 用户id
	 * @param avatar 头像的路径
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateAvatar(Integer uid, String avatar, String modifiedUser, Date modifiedTime) {
		Integer rows = userMapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		if(rows != 1) {
			throw new UpdateException("更新头像时出现未知错误");
		}
	}

	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	/**
	 * 根据用户id查询用户信息
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findByUid(Integer uid) {
		return userMapper.findByUid(uid);
	}
	
	
	
	
	
	
	
}
