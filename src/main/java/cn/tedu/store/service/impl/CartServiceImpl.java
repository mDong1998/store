package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层实现类
 * @author maggie
 *
 */
@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private CartMapper cartMapper;

	@Override
	public void addToCart(Cart cart, String username) throws InsertException, UpdateException {
		// 创建Date对象
		Date now = new Date();
		// 从参数cart中获取uid和gid
		Integer uid = cart.getUid();
		Long gid = cart.getGid();
		// 执行查询：Cart findByUidAndGid(Integer uid, Long gid)
		Cart data = findByUidAndGid(uid, gid);
		// 判断查询结果是否为null
		if(data == null) {
			// 是：表示该用户尚未添加该商品到购物车，则向参数对象cart中封装4个日志属性
			cart.setCreatedUser(username);
			cart.setCreatedTime(now);
			cart.setModifiedUser(username);
			cart.setModifiedTime(now);
			// -- 执行插入数据：void insert(Cart cart)
			insert(cart);
		} else {
			// 否：表示该用户已经添加该商品到购物车，则从查询结果中取出cid
			Integer cid = data.getCid();
			// -- num值为参数cart中的num加上前序查询结果中的num
			Integer num = cart.getNum() + data.getNum();
			// -- 执行修改数量：void updateNum(Integer cid, Integer num, String modifiedUser, Date modifiedTime)
			updateNum(cid, num, username, now);			
		}
	}

	@Override
	public Integer addNum(Integer cid, Integer uid, String username)
			throws UpdateException, CartNotFoundException, CartNotFoundException {
		//根据参数cid查询数据
		Cart cart = findByCid(cid);
		//判断查询结果是否为null
		if(cart == null) {
			//是：抛出异常 CartNotFoundException
			throw new CartNotFoundException("购物车数据不存在！");
		}
		//判断查询结果中的uid与参数uid是否不同
		if(!cart.getUid().equals(uid)) {
			//是：抛出异常 CartNotFoundException
			throw new AccessDeniedException("访问权限不足！");
		}
		//从查询结果中取出当前的num
		Integer num = cart.getNum();
		//将num自增
		num++;
		//创建当前时间对象
		Date now = new Date();
		//更新
		updateNum(cid, num, username, now);
		//返回
		return num;
	}

	@Override
	public List<CartVO> getListByUid(Integer uid) {
		return findListByUid(uid);
	}

	@Override
	public List<CartVO> getListByCids(Integer[] cids) {
		return findListByCids(cids);
	}

	/**
	 * 插入购物车数据
	 * @param cart 购物车数据
	 */
	private void insert(Cart cart) {
		Integer rows = cartMapper.insert(cart);
		if(rows != 1) {
			throw new InsertException("插入数据时出现未知错误！");
		}
	}

	/**
	 * 修改购物车中的商品的数量
	 * @param cid 购物车id
	 * @param num 原商品数量
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateNum(Integer cid, Integer num, String modifiedUser, Date modifiedTime) {
		Integer rows = cartMapper.updateNum(cid, num, modifiedUser, modifiedTime);
		if(rows != 1) {
			throw new UpdateException("更新数据时出现未知错误！");
		}
	}

	/**
	 * 根据用户id和商品id查询购物车数据
	 * @param uid 用户id
	 * @param gid 商品id
	 * @return 匹配的购物车数据，如果没有匹配的，则返回null
	 */
	private Cart findByUidAndGid(Integer uid, Long gid) {
		return cartMapper.findByUidAndGid(uid, gid);
	}
	
	/**
	 * 获取指定用户的购物车数据列表
	 * @param uid 用户id
	 * @return 该用户的购物车列表
	 */
	private List<CartVO> findListByUid(Integer uid){
		return cartMapper.findListByUid(uid);
	}
	

	/**
	 * 根据购物车数据id查询购物车数据
	 * @param cid 购物车数据id
	 * @return 购物车数据，没有匹配的则返回null
	 */
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	}
	
	/**
	 * 查询指定的某些id的购物车数据
	 * @param cids 多个购物车数据的id的数组
	 * @return 指定的某些id的购物车数据
	 */
	private List<CartVO> findListByCids(Integer[] cids){
		return cartMapper.findListByCids(cids);
	}
	
}
