package controller;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import dao.AeropuertoDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Aeropuerto;
import model.Avion;

public class ActividadLControllerActivarDesactivar implements Initializable{

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Aeropuerto> cbAeropuerto;

    @FXML
    private ComboBox<String> cbAvion;

    @FXML
    private RadioButton rbActivado;

    @FXML
    private RadioButton rbDesactivado;

    @FXML
    private ToggleGroup tgEstado;
    
    private AeropuertoDao aDao= new AeropuertoDao();

    @FXML
    void cancelar(ActionEvent event) {
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage) node.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void guardar(ActionEvent event) {
    	Integer id_aeropuerto = cbAeropuerto.getSelectionModel().getSelectedItem().getId();
    	String modelo = cbAvion.getSelectionModel().getSelectedItem();
    	boolean activado = rbActivado.isSelected();
    	boolean resp=aDao.modificarAvion(id_aeropuerto,modelo,activado);
    	if (resp) {
    		ActividadLControllerAeropuertosAviones.ventanaAlerta("I", "Avión Modificado correctamente.");
    	}else {
    		ActividadLControllerAeropuertosAviones.ventanaAlerta("E", "No se pudo modificar el avión.");
    	}
    }

    @FXML
    void seleccionarAeropuerto(ActionEvent event) {
    	Integer id_aeropuerto = cbAeropuerto.getSelectionModel().getSelectedItem().getId();
    	ObservableList<Avion>listaAviones=aDao.cargarAviones(id_aeropuerto);
    	Iterator<Avion>it = listaAviones.iterator();
    	cbAvion.getItems().clear();
    	while (it.hasNext()) {cbAvion.getItems().add(it.next().getModelo());}    	
    }
    
    @FXML
    void seleccionarAvion(ActionEvent event) {
    	Avion a = aDao.filtrarAvion(cbAvion.getSelectionModel().getSelectedItem());    	
    	if (a.getActivado()) {
    		rbActivado.setSelected(true);
    	}else {
    		rbDesactivado.setSelected(true);
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbAeropuerto.setItems(aDao.cargarTodosAeropuertos());
		
	}

}
