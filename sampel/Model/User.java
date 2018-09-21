package Model;

public class User {
	private String name;
	private String surname;
	private String patronymic;
	private String password;
	private String login;

	public User(String name, String surname, String patronymic, String password, String login) {
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
		this.password = password;
		this.login = login;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
