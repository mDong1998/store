package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTestCase {

	@Autowired
	private IOrderService service;

	@Test
	public void createOrder() {
		try {
			Integer uid = 3;
			Integer aid = 26;
			Integer[] cids = {8, 6};
			String username = "mama";
			Order order = service.createOrder(uid, aid, cids, username);
			System.err.println(order);
			
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}

}