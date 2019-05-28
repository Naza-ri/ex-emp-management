package jp.co.sample.form;

/**
 * ログイン時に使用するフォーム.
 * @author risa.nazato
 *
 */
public class LoginForm {
	/**メールアドレス	 */
	public String mailAddress;
	/**パスワード	 */
	public String password;
	
	@Override
	public String toString() {
		return "Administrator [ mailAddress=" + mailAddress + ", password=" + password + "]";
	}
	
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
