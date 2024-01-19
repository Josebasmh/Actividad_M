package controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import dao.AeropuertoDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.RegistroTabla;

public class ActividadLControllerAniadirAeropuerto implements Initializable{

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Label lbFinanciacion;
    
    @FXML
    private Label lbTrabajadores;
    
    @FXML
    private ToggleGroup pp;

    @FXML
    private RadioButton rbPrivado;

    @FXML
    private RadioButton rbPublico;

    @FXML
    private TextField tfAnio;

    @FXML
    private TextField tfCalle;

    @FXML
    private TextField tfCapacidad;

    @FXML
    private TextField tfCiudad;

    @FXML
    private TextField tfFinanciacion;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfPais;

    @FXML
    private TextField tfTrabajadores;
    
    // Variables de clase
    private AeropuertoDao aDao= new AeropuertoDao();
    private RegistroTabla r;
    private boolean bPrivado;
    private ArrayList<String>listaNulos=new ArrayList<String>();
	private ArrayList<String>listaNumeros=new ArrayList<String>();

    @FXML
    void cancelar(ActionEvent event) {
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage) node.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void guardar(ActionEvent event) {  	
    	try {
    		comprobar();
    		if (ActividadLControllerAeropuertosAviones.registro.getId()==0) {aniadir(event);}
    		else {modificar(event);}
    	}catch(NullPointerException e) {    		
    		Iterator<String> it= listaNulos.iterator();
    		String mensaje="";
    		while (it.hasNext()) {
    			mensaje += it.next() + "\n";
    		}
    		ActividadLControllerLogeo.ventanaAlerta("E", mensaje);
    	}catch(NumberFormatException e) {    		
    		Iterator<String> it= listaNumeros.iterator();
    		String mensaje="";
    		while (it.hasNext()) {
    			mensaje += it.next() + "\n";
    		}
    		ActividadLControllerLogeo.ventanaAlerta("E", mensaje);
    		
    	}
    }

    @FXML
    void publicoPrivado(ActionEvent event) {
    	if (rbPrivado.isSelected()) {    		
    		tfTrabajadores.setVisible(false);
			lbTrabajadores.setText("");
			lbFinanciacion.setText("Nº Socios:");
    	}else {    		
    		tfTrabajadores.setVisible(true);
    		lbFinanciacion.setText("Financiación:");
    		lbTrabajadores.setText("Número de trabajadores:");
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Creación de variables para no escribir toda esa parrafada. 
		r=ActividadLControllerAeropuertosAviones.registro;
		bPrivado= ActividadLControllerAeropuertosAviones.bPrivado;
		
		if (r.getId()!=0) {
			tfNombre.setText(r.getNombre());
			tfPais.setText(r.getPais());
			tfCiudad.setText(r.getCiudad());
			tfCalle.setText(r.getCalle());
			tfNumero.setText(r.getNumero().toString());
			tfAnio.setText(r.getAnio().toString());
			tfCapacidad.setText(r.getCapacidad().toString());
			if (bPrivado) {
				rbPrivado.setSelected(true);
				tfTrabajadores.setVisible(false);
				lbTrabajadores.setText("");
				lbFinanciacion.setText("Nº Socios:");
				tfFinanciacion.setText(r.getSocios().toString());
			}else {
				rbPublico.setSelected(true);
				tfTrabajadores.setText(r.getNum_trabajadores().toString());
				tfFinanciacion.setText(r.getFinanciacion().toString());
			}
			rbPrivado.setDisable(true);
			rbPublico.setDisable(true);
		}else {
			rbPublico.setSelected(true);
		}
	}
    
    private void aniadir(ActionEvent event) {
    	int id=aDao.generarID("aeropuertos");
    	String nombre=tfNombre.getText();
    	String pais=tfPais.getText();
    	String ciudad=tfCiudad.getText();
    	String calle=tfCalle.getText();
    	Integer numero=Integer.parseInt(tfNumero.getText());
    	Integer anio=Integer.parseInt(tfAnio.getText());
    	Integer capacidad=Integer.parseInt(tfCapacidad.getText());
    	RegistroTabla rt;
    	
    	if (rbPrivado.isSelected()) {
    		Integer socios=Integer.parseInt(tfFinanciacion.getText());
    		rt=new RegistroTabla(id, nombre, pais, ciudad, calle, numero, anio, capacidad, socios);
    		
    	}else {
    		Float financiacion=Float.valueOf(tfFinanciacion.getText());
    		Integer trabajadores=Integer.parseInt(tfTrabajadores.getText());
    		rt=new RegistroTabla(id, nombre, pais, ciudad, calle, numero, anio, capacidad, financiacion, trabajadores);
    	}
    	
    	aDao.aniadirRegistro(rt,rbPrivado.isSelected());    	
    	
    	cancelar(event);
    }
    private void modificar(ActionEvent event) {
    	int id=r.getId();
    	String nombre=tfNombre.getText();
    	String pais=tfPais.getText();
    	String ciudad=tfCiudad.getText();
    	String calle=tfCalle.getText();
    	Integer numero=Integer.parseInt(tfNumero.getText());
    	Integer anio=Integer.parseInt(tfAnio.getText());
    	Integer capacidad=Integer.parseInt(tfCapacidad.getText());
    	RegistroTabla rt;
    	
    	if (rbPrivado.isSelected()) {
    		Integer socios=Integer.parseInt(tfFinanciacion.getText());
    		rt=new RegistroTabla(id, nombre, pais, ciudad, calle, numero, anio, capacidad, socios);
    		
    	}else {
    		Float financiacion=Float.valueOf(tfFinanciacion.getText());
    		Integer trabajadores=Integer.parseInt(tfTrabajadores.getText());
    		rt=new RegistroTabla(id, nombre, pais, ciudad, calle, numero, anio, capacidad, financiacion, trabajadores);
    	}
    	aDao.modificarRegistro(rt,rbPrivado.isSelected());
    	aDao.cargarAeropuertos(bPrivado);
    	cancelar(event);
    }
    
    private void comprobar() {
    	listaNulos.clear();
    	listaNumeros.clear();
    	
    	comprobarCamposNulos();
    	comprobarCamposNumero();
    	
    	if (!listaNulos.isEmpty()) throw new NullPointerException();
    	if (!listaNumeros.isEmpty()) throw new NumberFormatException();
    }
    private void comprobarCamposNulos() {

    	if (tfNombre.getText().isEmpty()) listaNulos.add("Campo Nombre no contiene valor");
    	if (tfPais.getText().isEmpty()) listaNulos.add("Campo País no contiene valor");
    	if (tfCalle.getText().isEmpty()) listaNulos.add("Campo Ciudad no contiene valor");
    	if (tfNumero.getText().isEmpty()) listaNulos.add("Campo Número no contiene valor");
    	if (tfAnio.getText().isEmpty()) listaNulos.add("Campo Año de inauguración no contiene valor");
    	if (tfCapacidad.getText().isEmpty()) listaNulos.add("Campo Capacidad no contiene valor");
    	if (bPrivado) {
    		if (tfFinanciacion.getText().isEmpty()) listaNulos.add("Campo Nº Socios no contiene valor");
    	}else {
    		if (tfFinanciacion.getText().isEmpty()) listaNulos.add("Campo Financiación no contiene valor");
    		if (tfTrabajadores.getText().isEmpty()) listaNulos.add("Campo Número de trabajadores no contiene valor");
    	}  	
    }
    private void comprobarCamposNumero() {
    	if (!tfNumero.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) listaNumeros.add("Campo Número debe ser mayor que 0");
    	if (!tfAnio.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) listaNumeros.add("Campo Año de inauguración debe ser mayor que 0");
    	if (!tfCapacidad.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) listaNumeros.add("Campo Capacidad debe ser mayor que 0");
    	if (bPrivado) {
    		if (!tfFinanciacion.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) listaNumeros.add("Campo Nº Socios debe ser mayor que 0");
    	}else {
    		if (!tfFinanciacion.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) listaNumeros.add("Campo Financiación debe ser mayor que 0");
    		if (!tfTrabajadores.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) listaNumeros.add("Campo Número de trabajadores debe ser mayor que 0");
    	}    	
    }
}
