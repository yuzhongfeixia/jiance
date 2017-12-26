package test;

import org.jeecgframework.core.util.PasswordUtil;

public class PasswordTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String password = PasswordUtil.encrypt("admin", "86263439", PasswordUtil.getStaticSalt());
		System.out.println(password);
	}

}
