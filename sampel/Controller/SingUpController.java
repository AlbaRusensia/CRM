package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Datebase.DatebaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SingUpController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button singUpButton;

	@FXML
	private TextField singUpPatronymic;

	@FXML
	private PasswordField password;

	@FXML
	private TextField singUpSurname;

	@FXML
	private TextField singUpName;

	@FXML
	private TextField login;

	@FXML
	private Button backward;

	@FXML
	void initialize() {

		singUpButton.setOnAction(event -> {
			singUpNewUser();
		});

		backward.setOnAction(event -> {
			NewScen newScen =  new NewScen();
			newScen.openNewScen("../View/sampel.fxml");
		});

	}

	private void singUpNewUser() {
		DatebaseHandler dbHandler = new DatebaseHandler();
		String name = singUpName.getText();
		String surname = singUpSurname.getText();
		String patronymic = singUpPatronymic.getText();
		String passwordString = password.getText();
		String loginString = login.getText();
		
		User user = new User(name, surname, patronymic, passwordString, loginString);

		dbHandler.signUpUser(user);
		
	}
}