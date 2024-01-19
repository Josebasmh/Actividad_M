package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;

import dao.AeropuertoDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Avion;
import model.RegistroTabla;

public class ActividadLControllerAeropuertosAviones implements Initializable{

    @FXML
    private ToggleGroup grupoRadioButton;
    
    @FXML
    private Menu menuAeropuertos;

    @FXML
    private Menu menuAviones;

    @FXML
    private RadioButton rbPrivado;

    @FXML
    private RadioButton rbPublico;
    
    @FXML
    private TableView<RegistroTabla> tvTabla;

    @FXML
    private TableColumn<RegistroTabla, Integer> tcAño;

    @FXML
    private TableColumn<RegistroTabla, String> tcCalle;

    @FXML
    private TableColumn<RegistroTabla, Integer> tcCapacidad;

    @FXML
    private TableColumn<RegistroTabla, String> tcCiudad;
    
    @FXML
    private TableColumn<RegistroTabla, Integer> tcFinanciacion;

    @FXML
    private TableColumn<RegistroTabla, Integer> tcId;

    @FXML
    private TableColumn<RegistroTabla, String> tcNombre;

    @FXML
    private TableColumn<RegistroTabla, Integer> tcNumero;

    @FXML
    private TableColumn<RegistroTabla, String> tcPais;

    @FXML
    private TableColumn<RegistroTabla, Integer> tcSocios;
    
    @FXML
    private TableColumn<RegistroTabla, Integer> tcTrabajadores;

    @FXML
    private TextField tfFiltro;
    
    // Variables de clase
    private AeropuertoDao aDao= new AeropuertoDao();    
    static RegistroTabla registro= new RegistroTabla(); 
    static boolean bPrivado=true;
    

    @FXML
    void filtrarPorNombre(KeyEvent event) {
    	tvTabla.setItems(aDao.filtrar(bPrivado,tfFiltro.getText()));
    }

    @FXML
    void publicoPrivado(ActionEvent event) {
    	if (rbPrivado.isSelected()){
    		bPrivado=true;
    	}else{
    		bPrivado=false;
    	}
    	if (rbPublico.isSelected()) {
    		tcSocios.setVisible(false);
    		tcFinanciacion.setVisible(true);
    		tcTrabajadores.setVisible(true);
    		tcCapacidad.setPrefWidth(105);
    	}else {
    		tcSocios.setVisible(true);
    		tcFinanciacion.setVisible(false);
    		tcTrabajadores.setVisible(false);
    		tcCapacidad.setPrefWidth(238);
    	}
    	rellenarTabla();
    }
    
    @FXML
    void activarDesactivar(ActionEvent event) {
    	crearVentanaAux("activarDesactivar","ACTIVAR/DESACTIVAR AVIÓN",600,400);
    }

    @FXML
    void aniadir(ActionEvent event) {
    	registro.setId(0);
    	crearVentanaAux("aniadirAeropuerto","AÑADIR AEROPUERTO",400,600);
    	tvTabla.setItems(aDao.cargarAeropuertos(bPrivado));
    }
    
    @FXML
    void aniadirAvion(ActionEvent event) {
    	crearVentanaAux("aniadirAvion","AÑADIR AVIÓN",600,400);
    }

    @FXML
    void borrar(ActionEvent event) {    	
    	try {
    		RegistroTabla registrotabla= tvTabla.getSelectionModel().getSelectedItem();
        	aDao.borrarRegistro(registrotabla,rbPrivado.isSelected());
        	tvTabla.setItems(aDao.cargarAeropuertos(bPrivado));
    	}catch(NullPointerException e) {
    		ActividadLControllerLogeo.ventanaAlerta("E", "Seleccione un registro de la tabla. Si no hay, añada uno");
       	}catch(SQLException e) {
       		ActividadLControllerLogeo.ventanaAlerta("E", "No se pudo borrar debido a que contiene aviones.");
       	}
    	
    }
    
    @FXML
    void borrarAvion(ActionEvent event) {
    	crearVentanaAux("borrarAvion","BORRAR AVIÓN",600,400);
    }

    @FXML
    void editar(ActionEvent event) {
    	try {
    		RegistroTabla registrotabla= tvTabla.getSelectionModel().getSelectedItem();
    		if  (bPrivado) {
    			registro=new RegistroTabla(registrotabla.getId(),registrotabla.getNombre(), registrotabla.getPais(), registrotabla.getCiudad(), registrotabla.getCalle(), registrotabla.getNumero(),
    					registrotabla.getAnio(), registrotabla.getCapacidad(), registrotabla.getSocios());
    		}else {
    			registro=new RegistroTabla(registrotabla.getId(),registrotabla.getNombre(), registrotabla.getPais(), registrotabla.getCiudad(), registrotabla.getCalle(), registrotabla.getNumero(),
    					registrotabla.getAnio(), registrotabla.getCapacidad(), registrotabla.getFinanciacion(),registrotabla.getNum_trabajadores());
    		}
    		crearVentanaAux("aniadirAeropuerto","MODIFICAR AEROPUERTO",1020,600);
    		tvTabla.setItems(aDao.cargarAeropuertos(bPrivado));
    	}catch(NullPointerException e){
    		ActividadLControllerLogeo.ventanaAlerta("E", "Seleccione un registro de la tabla. Si no hay, añada uno.");
    	}
    }
    
    @FXML
    void informacion(ActionEvent event) {
    	try {
    		RegistroTabla rt = tvTabla.getSelectionModel().getSelectedItem();
        	String nombre = "Nombre: "+rt.getNombre();
        	String pais = "Pais: "+rt.getPais();
        	String direccion = "Dirección: "+rt.getCalle()+" "+rt.getNumero()+", "+rt.getCiudad();
        	String anio = "Año de inauguracion: "+rt.getAnio();
        	String capacidad = "Capacidad: "+rt.getCapacidad();
        	String socio="";
        	String financiacion="";
        	String nTrabajadores="";
        	if (rbPrivado.isSelected()) {
        		socio = "Privado\nNº Socios: " + rt.getSocios();
        	}else {
        		financiacion = "Público\nFinanciación: " + rt.getFinanciacion();
        		nTrabajadores= "Número de trabajadores: " + rt.getNum_trabajadores();
        	}
        	ObservableList<Avion>listaAvion = aDao.cargarAviones(rt.getId());
        	Iterator<Avion>it=listaAvion.iterator();
        	String txtAvion ="";
        	while(it.hasNext()) {
        		Avion a=it.next();
        		txtAvion+=a.toString();
        	}
        	String msg=nombre+"\n"+pais+"\n"+direccion+"\n"+anio+"\n"+capacidad+"\nAviones:\n"+txtAvion+"\n";
        	if(rbPrivado.isSelected()){
        		msg+=socio;
        	}else {
        		msg+=financiacion+"\n"+nTrabajadores;
        	}
        	ActividadLControllerLogeo.ventanaAlerta("I", msg);
    	}catch(NullPointerException e) {
    		ActividadLControllerLogeo.ventanaAlerta("E", "Seleccione un registro.");
    	}
    }    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rellenarTabla();
	}

	public void rellenarTabla() {
		ObservableList<RegistroTabla>listaRegistros = aDao.cargarAeropuertos(bPrivado);	
		
		tcId.setCellValueFactory(new PropertyValueFactory<RegistroTabla, Integer>("id"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<RegistroTabla, String>("nombre"));
		tcPais.setCellValueFactory(new PropertyValueFactory<RegistroTabla, String>("pais"));
		tcCiudad.setCellValueFactory(new PropertyValueFactory<RegistroTabla, String>("ciudad"));
		tcCalle.setCellValueFactory(new PropertyValueFactory<RegistroTabla, String>("calle"));
		tcNumero.setCellValueFactory(new PropertyValueFactory<RegistroTabla, Integer>("numero"));
		tcAño.setCellValueFactory(new PropertyValueFactory<RegistroTabla, Integer>("anio"));
		tcCapacidad.setCellValueFactory(new PropertyValueFactory<RegistroTabla, Integer>("capacidad"));
		if (bPrivado) {
			tcSocios.setCellValueFactory(new PropertyValueFactory<RegistroTabla, Integer>("socios"));			
		}else {
			tcTrabajadores.setCellValueFactory(new PropertyValueFactory<RegistroTabla, Integer>("num_trabajadores"));
			tcFinanciacion.setCellValueFactory(new PropertyValueFactory<RegistroTabla, Integer>("financiacion"));
		}
		tvTabla.setItems(listaRegistros);
	}
	
	void crearVentanaAux(String fxml, String titulo,Integer anchura,Integer altura) {
		
		// Creación de ventana
		Stage arg0 = new Stage();
		arg0.setTitle(titulo); 
		FlowPane aux;
		try {
			aux = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/"+fxml+".fxml"));
			Scene scene = new Scene(aux,anchura,altura);
			arg0.setScene(scene);
			arg0.setMinWidth(anchura);
			arg0.setMinHeight(altura);
			arg0.getIcons().add(new Image(getClass().getResource("/img/avion.png").toString()));
			arg0.initModality(Modality.APPLICATION_MODAL);
			arg0.show();
		} catch (IOException e) {
			System.out.println("La ventana no se abrió correctamente.");
			e.printStackTrace();
		}
	}
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
}
