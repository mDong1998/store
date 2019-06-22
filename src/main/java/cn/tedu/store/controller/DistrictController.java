package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {
	
	@Autowired
	private IDistrictService districtService;
	
	@GetMapping("/")
	public ResponseResult<List<District>> getListByParent(
			@RequestParam("parent") String parent){
		List<District> data = districtService.getListByParent(parent);
		return new ResponseResult<>(SUCCESS, data);
	}
	
	/*
	 * RESTful：
	 * 资源/id/命令：
	 * addresses/		-> 收货地址数据列表
	 * addresses/1/		-> 获取id=1的收货地址数据
	 * addresses/1/delete	-> 删除id=1的收货地址数据
	 * 参数不多，参数可控，参数不私密的情况下，使用：
	 * {}占位符需要结合PathVariable使用。{}内写什么，PathVariable()内就写什么
	 */
	@GetMapping("{code}")
	public ResponseResult<District> getByCode(@PathVariable("code") String code) {
		District data = districtService.getByCode(code);
		return new ResponseResult<>(SUCCESS, data);
	}
	
	
}