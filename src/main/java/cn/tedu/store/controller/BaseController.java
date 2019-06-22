package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.tedu.store.controller.ex.FileContentTypeException;
import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserConflictException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.util.ResponseResult;

/**
 * 控制器类的基类
 * @author maggie
 *
 */
public class BaseController {
	
	/**
	 * 处理请求时，用于表示操作正确的代码
	 */
	public static final int SUCCESS = 200;
	
	@ExceptionHandler({ServiceException.class, FileUploadException.class})
	public ResponseResult<Void> handleException(Exception e){
		//声明返回对象
		ResponseResult<Void> rr = new ResponseResult<>();
		//在返回对象中封装错误提示的文字
		rr.setMessage(e.getMessage());
		//根据异常的类型不同，返回的错误代码也不同
		if(e instanceof UserConflictException) {
			// 400-用户名冲突异常 
			rr.setState(400);
		} else if(e instanceof UserNotFoundException) {
			// 401-用户不存在异常
			rr.setState(401);
		} else if(e instanceof PasswordNotMatchException) {
			// 402-验证用户密码失败的异常
			rr.setState(402);
		} else if(e instanceof AddressNotFoundException) {
			// 403-指定收货地址不存在的异常
			rr.setState(403);
		} else if(e instanceof AccessDeniedException) {
			// 404-拒接访问的异常 
			rr.setState(404);
		} else if(e instanceof CartNotFoundException){
			// 405-购物车不存在的异常
			rr.setState(405);
		} else if(e instanceof InsertException) {
			// 500-插入数据异常
			rr.setState(500);
		} else if(e instanceof UpdateException) {
			// 501-更新数据异常
			rr.setState(501);
		} else if(e instanceof DeleteException) {
			// 502-删除数据异常
			rr.setState(502);
		} else if(e instanceof FileEmptyException) {
			// 601-上传的文件为空的异常
			rr.setState(601);
		} else if(e instanceof FileContentTypeException) {
			// 602-上传的文件类型错误的异常
			rr.setState(602);
		} else if(e instanceof FileSizeException) {
			// 603-上传的文件大小超出限制的异常
			rr.setState(603);
		}
		return rr;
	}
	
	/**
	 * 从Session中获取当前登录的用户的id
	 * @param session
	 * @return 当前登录的用户的id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}
	
	
	
}