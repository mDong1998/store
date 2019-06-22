package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTestCase {

	@Autowired
	CartMapper mapper;
	
	@Test
	public void insert() {
		Cart cart = new Cart();
		cart.setUid(3);
		cart.setGid(1001L);
		cart.setNum(1);
		Integer rows = mapper.insert(cart);
		System.err.println(rows);
	}
	
	@Test
	public void updateNum() {
		Integer cid = 1;
		Date modifiedTime = new Date();
		String modifiedUser = "Maggie";
		Integer num = 2;
		Integer rows = mapper.updateNum(cid, num, modifiedUser, modifiedTime);
		System.err.println(rows);
	}
	
	@Test
	public void findByUidAndGid() {
		 Integer uid = 3;
		 long gid = 1001L;
		 Cart cart = mapper.findByUidAndGid(uid, gid);
		 System.err.println(cart);
	}
	
	@Test
	public void findListByUid() {
		Integer uid = 7;
		List<CartVO> list = mapper.findListByUid(uid);
		System.err.println("BEGIN");
		for(CartVO c : list) {
			System.err.println(c);
		}
		System.err.println("END");
	}
	
	@Test
	public void findByCid() {
		Integer cid = 7;
		Cart cart = mapper.findByCid(cid);
		System.err.println(cart);
	}
	@Test
	public void findListByCids() {
		Integer[] cids = {6,5,7};
		List<CartVO> list = mapper.findListByCids(cids);
		System.err.println("BEGIN");
		for(CartVO c : list) {
			System.err.println(c);
		}
		System.err.println("END");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
