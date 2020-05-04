package ua.nure.tereshchenko.SummaryTask4.exception;
/**
 * An exception that provides information on a database access error.
 *
 *
 */
public class DBException extends AppException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5790983547500431944L;

	public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}