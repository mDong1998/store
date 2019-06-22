package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Goods;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTestCase {

	@Autowired
	private IGoodsService goodsService;

	@Test
	public void getHotGoods() {
		Integer count = 5;
		List<Goods> list = goodsService.getHotGoods(count);
		System.err.println("BEGIN");
		for (Goods g : list) {
			System.err.println(g);
		}
		System.err.println("END");
	}

	@Test
	public void getById() {
		long id = 10000017L;
		Goods data = goodsService.getById(id);
		System.err.println(data);
	}

}