package in.nit.helper;

public class UserFoundException extends Exception {

	public UserFoundException() {
		super("User with this username is already present in DB !! try with another one");
	}
	public UserFoundException(String msg) {
		super(msg);
	}
}
