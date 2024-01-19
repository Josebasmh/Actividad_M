package model;

public class Aeropuerto {

	int id;
	String nombre;
	Integer anio_inauguracion,capacidad,id_direccion;
	
	public Aeropuerto(int ID,String n, Integer a, Integer c, Integer id_di) {
		id=ID;
		nombre=n;
		anio_inauguracion=a;
		capacidad=c;
		id_direccion=id_di;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getAnio_inauguracion() {
		return anio_inauguracion;
	}

	public void setAnio_inauguracion(Integer anio_inauguracion) {
		this.anio_inauguracion = anio_inauguracion;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Integer getId_direccion() {
		return id_direccion;
	}

	public void setId_direccion(Integer id_direccion) {
		this.id_direccion = id_direccion;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
}
