package ua.nure.ivanovv.SummaryTask4.db.entity;

/**
 * User entity.
 * 
 * @author V.Ivanov
 * 
 */
public class User extends Entity {

	private static final long serialVersionUID = 4394061975930098620L;

	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private int roleId;
	private int sumPay;
	private int countBook;

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", roleId=" + roleId + ", sumPay=" + sumPay + ", countBook=" + countBook + "]";
	}

	public int getSumPay() {
		return sumPay;
	}

	public void setSumPay(int sumPay) {
		this.sumPay = sumPay;
	}

	public int getCountBook() {
		return countBook;
	}

	public void setCountBook(int countBook) {
		this.countBook = countBook;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
