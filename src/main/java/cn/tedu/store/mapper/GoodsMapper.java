package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Goods;

/**
 * 处理商品数据的处久层接口
 * @author maggie
 *
 */
public interface GoodsMapper {

	/**
	 * 获取热销商品的列表
	 * @param count 需要获取的商品的数量
	 * @return 热销商品的列表
	 */
	List<Goods> findHotGoods(Integer count);
	
	/**
	 * 根据商品id查询商品数据
	 * @param id 商品id
	 * @return 商品数据信息，如果没有匹配的数据，则返回null
	 */
	Goods findById(long id);
	
	
	
}
