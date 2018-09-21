package Controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class NewScen {
	public void openNewScen(String windows) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(windows));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parent root = loader.getRoot();
		Main.primaryStage.setScene(new Scene(root));
		Main.primaryStage.show();
	}

}
