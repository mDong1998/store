package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 处理收货地址数据的业务层接口
 * @author maggie
 *
 */
public interface IAddressService {
	
	/**
	 * 插入新的收货地址数据
	 * @param address 收货地址数据
	 * @param username 所属的用户的用户名
	 * @throws InsertException
	 */
	void addNew(Address address, String username) throws InsertException;
	
	/**
	 * 获取指定用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	List<Address> getListByUid(Integer uid);
	
	/**
	 * 将指定的收货地址设置为默认
	 * @param uid 当前登录的用户的id
	 * @param aid 收货地址id
	 * @param username 用户名
	 * @throws UpdateException
	 * @throws AddressNotFoundException
	 * @throws AccessDeniedException
	 */
	void setDefault(Integer uid, Integer aid, String username) throws UpdateException,
		AddressNotFoundException, AccessDeniedException;
	
	/**
	 * 删除指定收货地址
	 * @param uid 当前登录的用户的id
	 * @param aid 收货地址id
	 * @param username 用户名
	 * @throws DeleteException
	 * @throws AddressNotFoundException
	 * @throws AccessDeniedException
	 */
	void deleteByAid(Integer uid, Integer aid, String username) throws DeleteException,
		AddressNotFoundException, AccessDeniedException;
	
	/**
	 * 根据收货地址id查询收货地址数据
	 * @param aid 收货地址id
	 * @return 收货地址数据
	 */
	Address getByAid(Integer aid);
	
	
	
	
	
	
	
}
