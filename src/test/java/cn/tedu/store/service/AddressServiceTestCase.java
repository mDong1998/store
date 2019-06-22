package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTestCase {

	@Autowired
	private IAddressService service;

	@Test
	public void addNew() {
		try {
			Address address = new Address();
			address.setUid(3);
			address.setReceiver("张三");
			String username = "某个人";
			service.addNew(address, username);
			System.err.println("OK");			
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void setDefault() {
		try {
			Integer uid =7;
			Integer aid = 22;
			String username = "某个人";
			service.setDefault(uid, aid, username);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void deleteByAid() {
		try {
			Integer uid = 7;
			Integer aid = 23;
			String username = "单元测试";
			service.deleteByAid(uid, aid, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
}
