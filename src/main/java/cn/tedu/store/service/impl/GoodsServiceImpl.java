package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.mapper.GoodsMapper;
import cn.tedu.store.service.IGoodsService;

/**
 * 处理商品数据的业务层实现类
 * @author maggie
 *
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

	@Autowired
	GoodsMapper goodsMapper;
	
	@Override
	public List<Goods> getHotGoods(Integer count) {
		return findHotGoods(count);
	}

	@Override
	public Goods getById(long id) {
		return findById(id);
	}

	/**
	 * 查询指定数量的热门产品
	 * @param count limit 0,count
	 * @return 商品数据集合
	 */
	private List<Goods> findHotGoods(Integer count){
		return goodsMapper.findHotGoods(count);
	}
	/**
	 * 根据商品id查询商品数据
	 * @param id 商品id
	 * @return 商品数据信息，如果没有匹配的数据，则返回null
	 */
	private Goods findById(long id) {
		return goodsMapper.findById(id);
	}
	


}
