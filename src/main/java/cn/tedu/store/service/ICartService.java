package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层接口
 * @author maggie
 *
 */
public interface ICartService {
	
	/**
	 * 将商品添加到购物车
	 * @param cart 购物车数据，至少需要包括：用户id，商品id，商品数量
	 * @param username 当前登录的用户的用户名
	 * @throws InsertException
	 * @throws UpdateException
	 */
	void addToCart(Cart cart, String username) throws InsertException, UpdateException;
	
	/**
	 * 增加购物车数据中的商品数量
	 * @param cid 购物车数据id
	 * @param uid 用户数据id
	 * @param username 当前登录的用户的用户名
	 * @return 增加后的商品数量
	 * @throws UpdateException
	 * @throws AccessDeniedException
	 * @throws CartNotFoundException
	 */
	Integer addNum(Integer cid, Integer uid, String username) throws UpdateException,
		AccessDeniedException, CartNotFoundException;

	/**
	 * 获取指定用户的购物车数据列表
	 * @param uid 用户id
	 * @return 该用户的购物车列表
	 */
	List<CartVO> getListByUid(Integer uid);
	
	/**
	 * 查询指定的某些id的购物车数据
	 * @param cids 多个购物车数据的id的数组
	 * @return 指定的某些id的购物车数据
	 */
	List<CartVO> getListByCids(Integer[] cids);
	
	
	
	
	
	
}
