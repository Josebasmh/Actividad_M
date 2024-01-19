package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Propiedades;

public class ActividadLControllerLogeo implements Initializable{

    @FXML
    private Button btnLogin;

    @FXML
    private TextField tfContrasena;

    @FXML
    private TextField tfUsuario;

    /**
     * Comprueba que los datos introducidos son correctos. Si los son,
     * inicia la siguiente ventana. Si no lo son, en cambio, saldrá una 
     * ventana de error.
     * @param event
     */
    @FXML
    void logear(ActionEvent event) {
    	String sUsuario = tfUsuario.getText();
    	String sContrasena = tfContrasena.getText();
    	
    	String txtUsuario = Propiedades.getValor("usuario");
    	String txtContrasena = Propiedades.getValor("contrasena");
    	if(sUsuario.equals(txtUsuario)&&sContrasena.equals(txtContrasena)) {
    		crearVentanaAux();    		
    	}else {
    		ventanaAlerta("E", "Datos incorrectos.");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Crea una ventana para advertir o informar dependiendo del parámetro insertado.
	 * @param tipoAlerta Error("E") o Información("I").
	 * @param mensaje Mensaje a insertar en la ventana.
	 */
	static void ventanaAlerta(String tipoAlerta, String mensaje) {
		Alert alert = null;
		switch (tipoAlerta) {
			case ("E"):
				alert = new Alert(Alert.AlertType.ERROR);
				break;
			case ("I"):
				alert = new Alert(Alert.AlertType.INFORMATION);
		}
        alert.setContentText(mensaje);
        alert.showAndWait();
	}
	/**
	 * Crea la ventana de listadoAeropuertos.fxml.
	 * Captura IOException.
	 */
	void crearVentanaAux() {
		
		// Borrar los datos registrados del Logeo
		tfUsuario.setText("");
		tfContrasena.setText("");
		
		// Creación de ventana
		Stage arg0 = new Stage();
		arg0.setTitle("AVIONES - AEROPUERTOS"); 
		FlowPane aux;
		try {
			aux = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/listadoAeropuertos.fxml"));
			Scene scene = new Scene(aux,1020,600);
			arg0.setScene(scene);
			arg0.setMinWidth(1020);
			arg0.setMinHeight(600);
			arg0.getIcons().add(new Image(getClass().getResource("/img/avion.png").toString()));
			arg0.initModality(Modality.APPLICATION_MODAL);
			arg0.show();
		} catch (IOException e) {
			System.out.println("La ventana no se abrió correctamente.");
			e.printStackTrace();
		}
	}
}