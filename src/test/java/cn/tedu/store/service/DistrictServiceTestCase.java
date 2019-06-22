package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTestCase {

	@Autowired
	private IDistrictService service;
	
	@Test
	public void findListByParent() {
		String parent = "86";
		List<District> list = service.getListByParent(parent);
		System.err.println("BEGIN");
		for(District d : list) {
			System.err.println(d);			
		}
		System.err.println("END");
	}
	
	@Test
	public void findByCode() {
		String code = "140802";
		District district = service.getByCode(code);
		System.err.println(district);
	}
	
}
