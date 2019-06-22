package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTestCase {

	@Autowired
	private ICartService cartService;
	
	@Test
	public void addToCart() {
		try {
			Cart cart = new Cart();
			cart.setGid(1001L);
			cart.setUid(2);
			cart.setNum(-6);
			String username = "Super Manager";
			cartService.addToCart(cart, username);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void addNum() {
		try {
			String username = "Blackhole";
			Integer uid = 7;
			Integer cid = 8;
			Integer updatedNum = cartService.addNum(cid, uid, username);
			System.err.println(updatedNum);			
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void getListByUid() {
		Integer uid = 7;
		List<CartVO> list = cartService.getListByUid(uid);
		System.err.println("BEGIN");
		for(CartVO c : list) {
			System.err.println(c);
		}
		System.err.println("END");
	}
	
	@Test
	public void getListByCids() {
		Integer[] cids = {7,8,5};
		List<CartVO> list = cartService.getListByCids(cids);
		System.err.println("BEGIN");
		for(CartVO c : list) {
			System.err.println(c);
		}
		System.err.println("END");
	}
	
	
	
	
	
	
}