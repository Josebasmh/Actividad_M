package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Aeropuerto;
import model.Avion;
import model.RegistroTabla;

public class AeropuertoDao {

	private ConexionBD conexion;
	
	public AeropuertoDao() {
		try {
			conexion = new ConexionBD();
		} catch (SQLException e) {}
	}
	
	/**
	 * Carga los datos de la tabla dependiendo si elegimos aeropuertos publicos o privados.
	 * @param bPrivado
	 * @return ObservableList<RegistroTabla>
	 */
	public ObservableList<RegistroTabla> cargarAeropuertos(boolean bPrivado){
		ObservableList<RegistroTabla> listaRegistros = FXCollections.observableArrayList();
		try {			
			String consulta = "";
			if (bPrivado) {
				consulta= "SELECT * FROM aeropuertos a,aeropuertos_privados ap,direcciones d"
						+ " WHERE a.id=ap.id_aeropuerto"
						+ " AND d.id=a.id_direccion;";	
			}else {
				consulta= "SELECT * FROM aeropuertos a,aeropuertos_publicos ap,direcciones d "
						+ " WHERE ap.id_aeropuerto = a.id "
						+ " AND d.id=a.id_direccion;";
			}
			
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();   				
			while (rs.next()) {
				int nid=rs.getInt("a.id");
				String snombre=rs.getString("nombre");
				String spais = rs.getString("pais");
				String sciudad=rs.getString("ciudad");
				String scalle=rs.getString("calle");
				int nnumero=rs.getInt("numero");
				int nanio=rs.getInt("anio_inauguracion");
				int ncapacidad=rs.getInt("capacidad");
				RegistroTabla rt; 
				if(bPrivado) {
					Integer nsocios=rs.getInt("numero_socios");
					rt = new RegistroTabla(nid, snombre, spais, sciudad, scalle, nnumero, nanio, ncapacidad, nsocios);
				}else {
					Float nfinanciacion=rs.getFloat("financiacion");
					Integer ntrabajadores=rs.getInt("num_trabajadores");
					rt = new RegistroTabla(nid, snombre, spais, sciudad, scalle, nnumero, nanio, ncapacidad, nfinanciacion,ntrabajadores);
				}
				listaRegistros.add(rt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaRegistros;
	}
	
	public ObservableList<Aeropuerto> cargarTodosAeropuertos() {
		ObservableList<Aeropuerto> listaAeropuerto = FXCollections.observableArrayList();
		String consulta = "SELECT * FROM aeropuertos;";
		PreparedStatement ps;
		try {
			ps = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				Integer anio = rs.getInt("anio_inauguracion");
				Integer capacidad = rs.getInt("capacidad");
				Integer idDireccion = rs.getInt("id_direccion");
				Aeropuerto a = new Aeropuerto(id, nombre, anio, capacidad, idDireccion);
				listaAeropuerto.add(a);
			}
		} catch (SQLException e) {}
		return listaAeropuerto;
		
		
	}
	
	public int generarID(String tabla) {
		int id=-1;
		try {
			String consulta = "SELECT COUNT(*) FROM "+tabla+";";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {id=rs.getInt(1)+1;}
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
		
	}
	public void aniadirRegistro(RegistroTabla rt,boolean bPrivado) {
		//Datos tabla direccion
		int idDirecciones = generarID("direcciones");
		String pais=rt.getPais();
		String ciudad=rt.getCiudad();
		String calle=rt.getCalle();
		Integer numero=rt.getNumero();
		
		//Datos tabla aeropuerto
		int id=rt.getId();
		String nombre=rt.getNombre();
		Integer anio=rt.getAnio();
		Integer capacidad=rt.getCapacidad();
		
		//Datos tabla privado/publico
		String consultaPrivado="";
		String consultaPublica="";
		if (bPrivado) {
			int num_socio=rt.getSocios();
			consultaPrivado="INSERT INTO aeropuertos_privados values("+id+","+num_socio+");";
		}else {			
			Float financiacion=rt.getFinanciacion();
			Integer trabajadores=rt.getNum_trabajadores();
			consultaPublica="INSERT INTO aeropuertos_publicos values("+id+","+financiacion+","+trabajadores+")";
		}
				
		String consultaDirecciones="INSERT INTO direcciones values("+idDirecciones+",'"+pais+"','"+ciudad+"','"+calle+"',"+numero+");";
		String consultaAeropuertos="INSERT INTO aeropuertos values("+id+",'"+nombre+"',"+anio+","+capacidad+","+idDirecciones+",NULL);";
		PreparedStatement pstmt;
		try {
			pstmt = conexion.getConexion().prepareStatement(consultaDirecciones);
			pstmt.executeUpdate();
			pstmt.executeUpdate(consultaAeropuertos);
			if (bPrivado) {
				pstmt.executeUpdate(consultaPrivado);
			}else {
				pstmt.executeUpdate(consultaPublica);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void borrarRegistro(RegistroTabla r,boolean bPrivado) throws SQLException {
		String consultaIdDireccion = "SELECT id_direccion FROM aeropuertos WHERE id = " + r.getId() + ";";
		String consultaAeropuerto = "DELETE FROM aeropuertos WHERE id = " + r.getId() + ";";
		String consultaPri = "DELETE FROM aeropuertos_privados WHERE id_aeropuerto = " + r.getId() + ";";
		String consultaPub = "DELETE FROM aeropuertos_publicos WHERE id_aeropuerto = " + r.getId() + ";";
		
		PreparedStatement pstmt;
		int id=-1;
	
		pstmt = conexion.getConexion().prepareStatement(consultaIdDireccion);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			id=rs.getInt("id_direccion");
		}
		rs.close();
		if(bPrivado) {				
			pstmt.executeUpdate(consultaPri);
		}else {
			pstmt.executeUpdate(consultaPub);
		}
		
		pstmt.executeUpdate(consultaAeropuerto);
		String consultaDireccion = "DELETE FROM direcciones WHERE id = " + id + ";";
		pstmt.executeUpdate(consultaDireccion);
		pstmt.close();
	}
	
	public void modificarRegistro(RegistroTabla rt,boolean privado) {		
		int id=-1;
		String consultaAeropuerto="UPDATE aeropuertos SET nombre = '"+rt.getNombre()+"',anio_inauguracion = "+rt.getAnio()+",capacidad = "+rt.getCapacidad()+
				" WHERE id = "+rt.getId()+";";
		String consultaPri = "UPDATE aeropuertos_privados SET numero_socios = "+rt.getSocios()+" WHERE id_aeropuerto = "+rt.getId();
		String consultaPub= "UPDATE aeropuertos_publicos SET financiacion = "+rt.getFinanciacion()+",num_trabajadores = "+rt.getNum_trabajadores()+
				" WHERE id_aeropuerto = "+rt.getId()+";";
		String consultaIdDirecciones="SELECT id_direccion from aeropuertos where id = "+rt.getId()+";";
		String consultaDirecciones="UPDATE direcciones SET pais = '"+rt.getPais()+"',ciudad = '"+rt.getCiudad()+"',calle = '"+rt.getCalle()+"',numero = "+
		rt.getNumero()+" WHERE id = "+id+";";
		
		PreparedStatement pstmt;
		try {
			pstmt = conexion.getConexion().prepareStatement(consultaIdDirecciones);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				id=rs.getInt("id_direccion");
			}
			rs.close();
			if(privado) {
				pstmt.executeUpdate(consultaPri);
			}else {
				pstmt.executeUpdate(consultaPub);
			}
			
			pstmt.executeUpdate(consultaAeropuerto);
			pstmt.executeUpdate(consultaDirecciones);
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}		
	}
	public ObservableList<Avion>cargarAviones(int ida){
		ObservableList<Avion>listaAvion= FXCollections.observableArrayList();
		String consulta = "SELECT * FROM aviones WHERE id_aeropuerto = "+ida+";";
		try {
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {				
				int id = rs.getInt("id");
				String modelo = rs.getString("modelo");
				Integer num_asiento = rs.getInt(3);
				Integer velocidad = rs.getInt("velocidad_maxima");
				Boolean activado = rs.getBoolean("activado");
				Integer id_aeropuerto = rs.getInt("id_aeropuerto");
				listaAvion.add(new Avion(id, modelo, num_asiento, velocidad, id_aeropuerto, activado));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaAvion;
	}

	public ObservableList<RegistroTabla> filtrar(boolean bPrivado, String sNombre) {
		ObservableList<RegistroTabla>listaRegistros = FXCollections.observableArrayList();
		String consulta="";
		if (bPrivado) {
			consulta = "SELECT * FROM aeropuertos a,aeropuertos_privados ap,direcciones d WHERE ap.id_aeropuerto = a.id AND d.id=a.id_direccion"+
					" AND nombre LIKE '%"+sNombre+"%'";	
		}else {
			consulta = "SELECT * FROM aeropuertos a,aeropuertos_publicos ap,direcciones d WHERE ap.id_aeropuerto = a.id AND d.id=a.id_direccion"+
					" AND nombre LIKE '%"+sNombre+"%'";
		}
		try {
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int idAeropuertos = rs.getInt("a.id");
				String nombre = rs.getString("nombre");
				Integer anio = rs.getInt("anio_inauguracion");
				Integer capacidad = rs.getInt("capacidad");
				String pais = rs.getString("pais");
				String ciudad = rs.getString("ciudad");
				String calle = rs.getString("calle");
				Integer numero = rs.getInt("numero");
				RegistroTabla rt;
				if (bPrivado) {
					Integer numero_socios = rs.getInt("numero_socios");
					rt = new RegistroTabla(idAeropuertos, nombre, pais, ciudad, calle, numero, anio, capacidad, numero_socios);					
				}else {
					Float financiacion = rs.getFloat("financiacion");
					Integer num_trabajadores = rs.getInt("num_trabajadores");
					rt = new RegistroTabla(idAeropuertos, nombre, pais, ciudad, calle, numero, anio, capacidad, financiacion, num_trabajadores);
				}
				listaRegistros.add(rt);
			}			
		}catch(Exception e) {}
		return listaRegistros;
	}

	public Integer comprobarClaveAvion(String modelo, int id) {
		String consulta = "SELECT COUNT(*) FROM aviones WHERE modelo='"+modelo+"' AND id_aeropuerto="+id;
		PreparedStatement pstmt;
		Integer i=0;
		try {
			pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {i=rs.getInt(1);}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public boolean aniadirAvion(Avion a) {
		String consulta = "INSERT INTO aviones VALUES("+a.getId()+",'"+a.getModelo()+"',"+a.getNumero_asiento()+","+a.getVelocidad_maxima()+","+
				a.getActivado()+","+a.getId_aeropuerto()+");";
		try {
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			pstmt.executeUpdate(consulta);
			pstmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}

	public Avion filtrarAvion(String modelo) {
		Avion a = null;
		String consulta = "SELECT * FROM aviones WHERE modelo = '"+modelo+"';";
		try {
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {				
				int id = rs.getInt("id");
				Integer num_asiento = rs.getInt(3);
				Integer velocidad = rs.getInt("velocidad_maxima");
				Boolean activado = rs.getBoolean("activado");
				Integer id_aeropuerto = rs.getInt("id_aeropuerto");
				a = new Avion(id, modelo, num_asiento, velocidad, id_aeropuerto, activado);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {}
		return a;		
	}

	public boolean modificarAvion(Integer id_aeropuerto, String modelo, boolean activado) {
		Integer nActivado=0;
		if (activado) {nActivado=1;}
		String consulta = "UPDATE aviones SET activado = "+nActivado+" WHERE modelo = '"+modelo+"' AND id_aeropuerto = "+id_aeropuerto+";";
		try {
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			return false;			
		}
	}

	public boolean borrarAvion(Integer id_aeropuerto, String modelo) {
		String consulta = "DELETE FROM aviones WHERE id_aeropuerto="+id_aeropuerto+" AND modelo = '"+modelo+"';";
		try {
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			return false;			
		}
	}
}
