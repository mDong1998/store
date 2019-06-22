package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {

	@Autowired
	IOrderService orderService;

	@PostMapping("create")
	public ResponseResult<Order> create(Integer aid, Integer[] cids, HttpSession session){
		//从session中获取用户数据
		Integer uid = getUidFromSession(session);
		String username = session.getAttribute("username").toString();
		//执行
		Order order = orderService.createOrder(uid, aid, cids, username);
		//返回
		return new ResponseResult<>(SUCCESS, order);
	}
	
	
	
}
