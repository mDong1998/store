package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的持久层接口
 * @author maggie
 *
 */
public interface AddressMapper {

	/**
	 * 插入收货地址数据
	 * @param address 地址数据
	 * @return 受影响的行数
	 */
	Integer insert(Address address);
	
	/**
	 * 通过用户id获得该用户已保存的收货地址的数量
	 * @param uid 用户的id
	 * @return 该用户保存的地址的数量
	 */
	Integer getCountByUid(Integer uid);
	
	/**
	 * 获取指定用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	List<Address> findListByUid(Integer uid);
	
	/**
	 * 根据收货地址id获得该收货地址所属的用户的id
	 * @param aid 收货地址id
	 * @return 用户id
	 */
	Address findByAid(Integer aid);
	
	/**
	 * 将指定用户的所有收货地址均设置为“非默认”
	 * @param uid 用户的id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return
	 */
	Integer updateNonDefault(@Param("uid") Integer uid, @Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 将指定的收货地址设置为“默认”
	 * @param aid 收货地址id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return
	 */
	Integer updateDefault(@Param("aid") Integer aid, @Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 根据收货地址id删除该地址
	 * @param aid 收货地址id
	 * @return 受影响的行数
	 */
	Integer deleteByAid(Integer aid);
	
	/**
	 * 查询指定用户的最后修改的收货地址数据
	 * @param uid 指定用户的id
	 * @return 收货地址id
	 */
	Address findLastModifiedByUid(Integer uid);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}