package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileContentTypeException;
import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.ResponseResult;

/**
 * 处理用户数据请求的控制器类
 * @author maggie
 *
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	/**
	 * 允许上传的文件类型的集合
	 */
	public static final List<String> UPLOAD_CONTENT_TYPES = new ArrayList<>();

	/**
	 * 允许上传的文件的最大大小
	 */
	public static final long UPLOAD_MAX_SIZE = 2 * 1024 * 1024;
	
	/**
	 * 存储上传的文件的文件夹名称
	 */
	public static final String UPLOAD_DIR = "upload";
	
	/**
	 * 添加允许上传的文件类型
	 */
	static {
		UPLOAD_CONTENT_TYPES.add("image/jpeg");
		UPLOAD_CONTENT_TYPES.add("image/png");
		UPLOAD_CONTENT_TYPES.add("image/gif");
		UPLOAD_CONTENT_TYPES.add("image/bmp");
	}

	@PostMapping("reg")
	public ResponseResult<Void> handleReg(User user){
		userService.reg(user);
		return new ResponseResult<Void>(SUCCESS);
	}

	@PostMapping("login")
	public ResponseResult<User> handleLogin(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session){
		User user = userService.login(username, password);
		session.setAttribute("uid", user.getUid());
		session.setAttribute("username", user.getUsername());
		return new ResponseResult<>(SUCCESS, user);
	}

	@PostMapping("change_password")
	public ResponseResult<Void> handleChangePassword(
			@RequestParam("old_password") String oldPassword,
			@RequestParam("new_password") String newPassword,
			HttpSession session){
		userService.changePassword(getUidFromSession(session), oldPassword, newPassword);
		return new ResponseResult<Void>(SUCCESS); 
	}

	@PostMapping("change_info")
	public ResponseResult<Void> handleChanegInfo(User user, HttpSession session){
		Integer uid = getUidFromSession(session);
		user.setUid(uid);
		userService.changeInfo(user);
		return new ResponseResult<Void>(SUCCESS);
	}

	@PostMapping("change_avatar")
	public ResponseResult<String> handleChangeAvatar(HttpServletRequest request,
			@RequestParam("avatar") MultipartFile avatar){
		//判断上传文件是否为空
		if(avatar.isEmpty()) {
			// 是：抛出异常：FileEmptyException
			throw new FileEmptyException("上传失败！没有选择文件，或上传的文件为空");
		}
		// 判断文件类型是否不在允许的范围内
		String contentType = avatar.getContentType();
		if(!UPLOAD_CONTENT_TYPES.contains(contentType)) {
			// 是：抛出异常：FileContentTypeException
			throw new FileContentTypeException("上传失败！不支持上传"+contentType+"类型的文件");
		}
		// 判断文件大小是否超出了限制
		if(avatar.getSize() > UPLOAD_MAX_SIZE) {
			// 是：抛出异常：FileSizeException
			throw new FileSizeException("上传失败！文件太大，仅允许上传不超过" + UPLOAD_MAX_SIZE/1024/1024 + "M的文件！");
		}
		
		// 标准上传流程
		// 确定上传文件夹
		String parentPath = request.getServletContext().getRealPath(UPLOAD_DIR);
		File parent = new File(parentPath);
		if(!parent.exists()) {
			parent.mkdirs();
		}
		//获取原文件名 eg. DAY04.md
		String originalFileName = avatar.getOriginalFilename();
		//获取原扩展名
		int index = originalFileName.lastIndexOf(".");
		String suffix = "";
		if(index != -1) {
			suffix = originalFileName.substring(index);
		}
		// 确定上传的文件名
		String filename = UUID.randomUUID() + suffix;
		
		// 执行存储
		File dest = new File(parent, filename);
		try {
			avatar.transferTo(dest);
		} catch (IOException e) {
			throw new FileUploadIOException("上传失败！出现读写错误");
		}
		
		//从request中获取session再获取uid
		Integer uid = getUidFromSession(request.getSession());
		// 将上传的文件路径存储到数据库：service.changeAvatar(uid, avatar)
		String avatarUrl = "/" + UPLOAD_DIR + "/" + filename;
		userService.changeAvatar(uid, avatarUrl);
		
		ResponseResult<String> rr = new ResponseResult<>();
		rr.setState(SUCCESS);
		rr.setData(avatarUrl);
		return rr;
	}

	@RequestMapping("get_info")
	public ResponseResult<User> getInfo(HttpSession session){
		Integer uid = getUidFromSession(session);
		User user = userService.getByUid(uid);
		return new ResponseResult<User>(SUCCESS, user);
	}










}