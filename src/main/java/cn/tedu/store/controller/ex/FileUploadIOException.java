package cn.tedu.store.controller.ex;

/**
 * 上传文件时读写错误
 * @author maggie
 *
 */
public class FileUploadIOException extends FileUploadException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1234089097804788587L;

	public FileUploadIOException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileUploadIOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public FileUploadIOException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FileUploadIOException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FileUploadIOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
