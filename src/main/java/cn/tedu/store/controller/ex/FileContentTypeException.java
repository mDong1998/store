package cn.tedu.store.controller.ex;

/**
 * 文件格式错误异常
 * @author maggie
 *
 */
public class FileContentTypeException extends FileUploadException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -911131378972632004L;

	public FileContentTypeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileContentTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public FileContentTypeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FileContentTypeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FileContentTypeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
