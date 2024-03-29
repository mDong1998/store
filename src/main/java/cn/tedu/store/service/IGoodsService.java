package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Goods;

/**
 * 处理商品数据的业务层接口
 * @author maggie
 *
 */
public interface IGoodsService {
	
	/**
	 * 获取热销商品的列表
	 * @param count 需要获取的商品的数量
	 * @return 热销商品的列表
	 */
	List<Goods> getHotGoods(Integer count);
	
	/**
	 * 根据商品id查询商品数据
	 * @param id 商品id
	 * @return 商品数据信息，如果没有匹配的数据，则返回null
	 */
	Goods getById(long id);
	
	
	
	
}
