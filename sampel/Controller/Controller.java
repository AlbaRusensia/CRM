package Controller;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Animtions.Shake;
import Datebase.DatebaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button buttonEntering;

	@FXML
	private TextField textLogin;

	@FXML
	private PasswordField password;

	@FXML
	private Button buttonSignUp;

	@FXML
	private Label textError;

	@FXML
	void initialize() {
		buttonEntering.setOnAction(event -> {
			String loginText = textLogin.getText().trim();
			String loginPassword = password.getText().trim();

			if (!loginText.equals("") && !loginPassword.equals("")) {
				loginUser(loginText, loginPassword);
			} else {
				textError.setText("Введите логин и пароль");
				Shake userLoginAnim = new Shake(textLogin);
				Shake userPassword = new Shake(password);
				userLoginAnim.playAnim();
				userPassword.playAnim();
			}
		});

		buttonSignUp.setOnAction(event -> {
			NewScen newScen = new NewScen();
			newScen.openNewScen("../View/singUp.fxml");
		});
	}

	private void loginUser(String loginText, String loginPassword) {
		DatebaseHandler dbHandler = new DatebaseHandler();
		User user = new User();
		user.setLogin(loginText);
		user.setPassword(loginPassword);
		ResultSet result = dbHandler.getUser(user);

		int counter = 0;

		try {
			while (result.next()) {
				counter++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (counter >= 1) {
			NewScen newScen = new NewScen();
			newScen.openNewScen("../View/window.fxml");
		} else {
			textError.setText("Неверный логин или пароль");
			Shake userLoginAnim = new Shake(textLogin);
			Shake userPassword = new Shake(password);
			userLoginAnim.playAnim();
			userPassword.playAnim();
		}
	}

}
