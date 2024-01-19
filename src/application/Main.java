package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	FlowPane root;
	
	@Override
	public void start(Stage stage) throws Exception {
		try {

			root = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
			stage.setTitle("AVIONES - LOGIN");
			Scene scene = new Scene(root,410,210);
			stage.setScene(scene);
			stage.setMinWidth(410);
			stage.setMinHeight(210);
			stage.setMaxWidth(410);
			stage.setMaxHeight(210);
			stage.getIcons().add(new Image(getClass().getResource("/img/avion.png").toString()));
			stage.show();	
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
