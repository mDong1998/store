package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理订单数据的业务层实现类
 * @author maggie
 *
 */
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private ICartService cartService;

	@Override
	@Transactional
	public Order createOrder(Integer uid, Integer aid, Integer[] cids, String username) {
		// 根据cids查询对应的购物车数据：List<CartVO> getListByCids(Integer[] cids)
		List<CartVO> cartVOs = cartService.getListByCids(cids);
		// 遍历并计算总价：total_price
		Long totalPrice = 0L;
		for(CartVO cartVO : cartVOs) {
			totalPrice += cartVO.getPrice() * cartVO.getNum();
		}
		// 创建当前时间对象
		Date now = new Date();
		// 创建订单Order对象
		Order order = new Order();
		// 向Order对象封装uid
		order.setUid(uid);
		// 根据aid查询地址数据：addressService.getByAid(aid)
		Address address = addressService.getByAid(aid);
		// 判断查询结果，如果为null，抛出异常
		if(address == null) {
			throw new AddressNotFoundException("收货地址不存在！");
		}
		// 向Order对象封装receiver,phone,address
		order.setReceiver(address.getReceiver());
		order.setPhone(address.getPhone());
		order.setAddress(address.getDistrict() + address.getAddress());
		// 向Order对象封装state值为0
		order.setState(0);
		// 向Order对象封装orderTime为now
		order.setOrderTime(now);
		// 向Order对象封装total_price
		order.setTotalPrice(totalPrice);
		// 向Order对象封装4项日志
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifiedUser(username);
		order.setModifiedTime(now);
		
		// 执行插入订单数据：insertOrder(order);
		insertOrder(order);

		// 遍历查询结果
		for(CartVO cartVO : cartVOs) {
			// -- 创建OrderItem对象
			OrderItem orderItem = new OrderItem();
			// -- 向OrderItem对象中封装商品相关的5个数据
			orderItem.setGoodsId(cartVO.getGid());
			orderItem.setGoodsImage(cartVO.getImage());
			orderItem.setGoodsNum(cartVO.getNum());
			orderItem.setGoodsPrice(cartVO.getPrice());
			orderItem.setGoodsTitle(cartVO.getTitle());
			// 向OrderItem对象封装4项日志
			orderItem.setCreatedUser(username);
			orderItem.setCreatedTime(now);
			orderItem.setModifiedUser(username);
			orderItem.setModifiedTime(now);
			// -- 向OrderItem对象中封装oid：order.getOid()
			orderItem.setOid(order.getOid());
			// -- 执行插入订单商品数据：insertOrderItem(orderItem);
			insertOrderItem(orderItem);
		}
		
		//将t_goods表总的库存减少
		//删除t_cart表中对应的数据
		
		//返回
		return order;
	}

	/**
	 * 新增订单数据
	 * @param order 订单数据
	 */
	private void insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if(rows != 1) {
			throw new InsertException("创建订单时出现未知错误！");
		}
	}

	/**
	 * 新增订单商品数据
	 * @param orderItem 订单商品数据
	 */
	private void insertOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if(rows != 1) {
			throw new InsertException("创建订单商品时出现未知错误！");
		}
	}
}
