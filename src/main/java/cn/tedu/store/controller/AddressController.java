package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.util.ResponseResult;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {
	
	@Autowired
	private IAddressService addressService;
	
	@PostMapping("addnew")
	public ResponseResult<Void> addNew(Address address, HttpSession session){
		//从session中获取username
		String username = String.valueOf(session.getAttribute("username").toString());
		//从session中获取uid
		Integer uid = getUidFromSession(session);
		//将uid封装到address中
		address.setUid(uid);
		//调用业务层对象的addNew(address, username)
		addressService.addNew(address, username);
		return new ResponseResult<>(SUCCESS);
	}
	
	@GetMapping("/")
	public ResponseResult<List<Address>> getListByUid(HttpSession session){
		//从session中获取uid
		Integer uid = getUidFromSession(session);
		//通过业务层对象查询数据
		List<Address> data = addressService.getListByUid(uid);
		//返回：成功+数据
		return new ResponseResult<>(SUCCESS, data);
	}
	
	@PostMapping("{aid}/set_default")
	public ResponseResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session){
		//从session中获取uid
		Integer uid = getUidFromSession(session);
		//从session中获取username
		String username = String.valueOf(session.getAttribute("username").toString());
		//调用业务层对象的setDefault方法
		addressService.setDefault(uid, aid, username);
		return new ResponseResult<>(SUCCESS);
	}
	
	@PostMapping("{aid}/delete")
	public ResponseResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session){
		//从session中获取uid
		Integer uid = getUidFromSession(session);
		//从session中获取username
		String username = String.valueOf(session.getAttribute("username").toString());
		//调用业务层对象的setDefault方法
		addressService.deleteByAid(uid, aid, username);
		return new ResponseResult<>(SUCCESS);
	}
	
	
	
}