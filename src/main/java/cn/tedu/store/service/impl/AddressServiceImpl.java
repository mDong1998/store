package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 收货地址的业务层实现类
 * @author maggie
 *
 */
@Service
public class AddressServiceImpl implements IAddressService {

	@Autowired
	AddressMapper addressMapper;
	@Autowired
	private IDistrictService districtService;

	@Override
	public void addNew(Address address, String username) throws InsertException {
		// 确定district的值
		String district = getDistrictByCodes(address.getProvince(), address.getCity(), address.getArea());
		address.setDistrict(district);
		//获取当前用户的收货地址数量
		Integer count = getCountByUid(address.getUid());
		//如果count为0，设置isDefault为1，否则，设置为0
		address.setIsDefault(count == 0 ? 1 : 0);

		//4项日志
		Date now = new Date();
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);

		//执行插入数据
		insert(address);
	}

	@Override
	public List<Address> getListByUid(Integer uid) {
		return findListByUid(uid);
	}

	@Override
	@Transactional //该注解是为了实现数据库的事务问题，通过事务保障数据安全，保证多条SQL语句全部执行成功或全部失败
	public void setDefault(Integer uid, Integer aid, String username)
			throws UpdateException, AddressNotFoundException, AccessDeniedException {
		//根据aid查询数据
		Address data = findByAid(aid);
		//判断查询结果是否为null
		if(data == null) {
			//是，抛出AddressNotFoundException
			throw new AddressNotFoundException("收货地址不存在！");
		}
		//判断查询结果中的uid与当前方法参数中的uid是否不同
		if(!data.getUid().equals(uid)) {
			//是，抛出AccessDeniedException
			throw new AccessDeniedException("访问权限不足！");
		}
		//创建当前时间对象
		Date now = new Date();
		//将所以地址设置为非默认
		updateNonDefault(uid, username, now);
		//将指定地址设置为默认
		updateDefault(aid, username, now);
	}

	@Override
	@Transactional
	public void deleteByAid(Integer uid, Integer aid, String username)
			throws DeleteException, AddressNotFoundException, AccessDeniedException {
		// 根据aid查询即将删除的数据
		Address address = findByAid(aid);
		
		// 判断查询结果是否为null
		if(address == null) {
			// 是：抛出异常：AddressNotFoundException
			throw new AddressNotFoundException("收货地址不存在！");
		}

		// 判断查询结果的uid是否与参数uid不相同
		if(!address.getUid().equals(uid)) {
			// 是：抛出异常：AccessDeniedException
			throw new AccessDeniedException("访问权限不足！");
		}
		// 执行删除
		deleteByAid(aid);
		// 判断刚刚删除的数据（最开始找出来的数据）的isDefault是否为1
		if(address.getIsDefault() == 1) {
			// -- 查询当前用户还有多少条收货地址
			Integer count = getCountByUid(uid);
			// -- 判断数量是否 > 0
			if(count > 0) {
				// -- 是：找出最后一条收货地址的aid
				Integer lastModifiedAid = findLastModifiedByUid(uid).getAid();
				// -- -- 把找出来的数据设置为默认
				updateDefault(lastModifiedAid, username, new Date());
			}
		}
	}

	@Override
	public Address getByAid(Integer aid) {
		return findByAid(aid);
	}

	/**
	 * 插入收货地址数据
	 * @param address 地址数据
	 */
	private void insert(Address address) {
		Integer rows = addressMapper.insert(address);
		if(rows != 1) {
			throw new InsertException("添加收货地址失败！出现未知错误！");
		}
	}

	/**
	 * 通过用户id获得该用户已保存的收货地址的数量
	 * @param uid 用户的id
	 * @return 该用户保存的地址的数量
	 */
	private Integer getCountByUid(Integer uid) {
		return addressMapper.getCountByUid(uid);
	}

	/**
	 * 根据省市区的代号获取地区的名称
	 * @param province 省的代号
	 * @param city 市的代号
	 * @param area 区的代号
	 * @return
	 */
	private String getDistrictByCodes(String province, String city, String area) {
		String provinceName = "NULL";
		String cityName = "NULL";
		String areaName = "NULL";
		District p = districtService.getByCode(province);
		if(p != null) {
			provinceName = p.getName();
		}
		District c = districtService.getByCode(city);
		if(c != null) {
			cityName = c.getName();
		}
		District a = districtService.getByCode(area);
		if(a != null) {
			areaName = a.getName();
		}
		return provinceName + cityName + areaName;
	}

	/**
	 * 获取指定用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	private List<Address> findListByUid(Integer uid){
		return addressMapper.findListByUid(uid);
	}

	/**
	 * 根据收货地址id获得该收货地址所属的用户的id
	 * @param aid 收货地址id
	 * @return 用户id
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}

	/**
	 * 将指定用户的所有收货地址均设置为“非默认”
	 * @param uid 用户的id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateNonDefault(Integer uid, String modifiedUser, Date modifiedTime) {
		List<Address> list = findListByUid(uid);
		Integer rows = addressMapper.updateNonDefault(uid, modifiedUser, modifiedTime);
		if(rows != list.size()) {
			throw new UpdateException("出现未知错误！");
		}
	}

	/**
	 * 将指定的收货地址设置为“默认”
	 * @param aid 收货地址id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateDefault(Integer aid, String modifiedUser, Date modifiedTime) {
		Integer rows = addressMapper.updateDefault(aid, modifiedUser, modifiedTime);
		if(rows != 1) {
			throw new UpdateException("出现未知错误！");
		}
	}

	/**
	 * 根据收货地址数据的id删除数据
	 * @param aid 收货地址数据的id
	 */
	private void  deleteByAid(Integer aid) {
		Integer rows = addressMapper.deleteByAid(aid);
		if (rows != 1) {
			throw new DeleteException("删除数据时出现未知错误！");
		}
	}
	
	/**
	 * 查询指定用户的最后修改的收货地址数据
	 * @param uid 指定用户的id
	 * @return 收货地址id
	 */
	private Address findLastModifiedByUid(Integer uid) {
		return addressMapper.findLastModifiedByUid(uid);
	}
}
