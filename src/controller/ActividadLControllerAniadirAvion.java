package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.AeropuertoDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Aeropuerto;
import model.Avion;

public class ActividadLControllerAniadirAvion implements Initializable{

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ChoiceBox<Aeropuerto> cbAeropuerto;

    @FXML
    private RadioButton rbActivado;

    @FXML
    private RadioButton rbDesactivado;

    @FXML
    private TextField tfAsientos;

    @FXML
    private TextField tfModelo;

    @FXML
    private TextField tfVelMax;

    @FXML
    private ToggleGroup tgEstado;
    
    private AeropuertoDao aDao = new AeropuertoDao(); 

    @FXML
    void cancelar(ActionEvent event) {
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage) node.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void guardar(ActionEvent event) {
    	boolean claveCorrecta= comprobarClave();
    	String camposMal = comprobarCampos();
    	if (claveCorrecta==false) {
    		ActividadLControllerAeropuertosAviones.ventanaAlerta("E", "Ese avión ya está registrado en el aeropuerto.");
    	}else if(!camposMal.equals("")){
    		ActividadLControllerAeropuertosAviones.ventanaAlerta("E", camposMal);
    	}else {
    		int id=aDao.generarID("aviones");
    		Avion a = new Avion(id, tfModelo.getText(), Integer.parseInt(tfAsientos.getText()), Integer.parseInt(tfVelMax.getText()), 
    				cbAeropuerto.getSelectionModel().getSelectedItem().getId(), rbActivado.isSelected());
    		aDao.aniadirAvion(a);
    		ActividadLControllerAeropuertosAviones.ventanaAlerta("I", "Avion insertado correctamente.");
    	}
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	cbAeropuerto.setItems(aDao.cargarTodosAeropuertos()); 		
    }
    
	private String comprobarCampos() {
		String msgError="";
		if(tfModelo.getText().equals("")) msgError+= "El modelo no puede tener valor nulo.";
		if (!tfAsientos.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) msgError+= "Numero de asientos debe ser mayor que 0";
		if (!tfVelMax.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) msgError+= "Numero de asientos debe ser mayor que 0";
		if (cbAeropuerto.getSelectionModel().getSelectedItem()==null) msgError+= "El modelo no puede tener valor nulo.";
		return msgError;
	}

	private boolean comprobarClave() {
		Integer cont = aDao.comprobarClaveAvion(tfModelo.getText(),cbAeropuerto.getSelectionModel().getSelectedItem().getId());
		if (cont > 0) {
			return false;
		}else {
			return true;
		}
		
	}


}
