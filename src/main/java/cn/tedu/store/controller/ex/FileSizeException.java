package cn.tedu.store.controller.ex;

/**
 * 文件大小异常，当上传的文件的大小超出了限制时出现该异常
 * @author maggie
 *
 */
public class FileSizeException extends FileUploadException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3652563516851916279L;

	public FileSizeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public FileSizeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FileSizeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FileSizeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
