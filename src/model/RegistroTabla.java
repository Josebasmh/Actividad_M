package model;

import java.util.Objects;

public class RegistroTabla {

	private int id;
	private Integer capacidad,numero,anio,socios,num_trabajadores;
	private String nombre,pais,ciudad,calle;
	private Float financiacion;
	
	/**
	 * Constructor para registro de aeropuerto privado.
	 * @param i id
	 * @param no nombre
	 * @param p pais
	 * @param ci ciudad
	 * @param ca calle
	 * @param nu número
	 * @param a año
	 * @param c capacidad
	 * @param ns Nº socios
	 */
	public RegistroTabla(int i,String no,String p,String ci,String ca,Integer nu,Integer a,Integer c,Integer ns) {
		setId(i);
		setNombre(no);
		setPais(p);
		setCiudad(ci);
		setCalle(ca);
		setNumero(nu);
		setAnio(a);
		setCapacidad(c);
		setSocios(ns);
		setFinanciacion(null);
		setNum_trabajadores(null);
	}
	/**
	 * Constructor para registro de aeropuerto público.
	 * @param i id
	 * @param no nombre
	 * @param p pais
	 * @param ci ciudad
	 * @param ca calle
	 * @param nu número
	 * @param a año
	 * @param c capacidad
	 * @param f financiación
	 * @param t Nº trabajadores
	 */
	public RegistroTabla(int i,String no,String p,String ci,String ca,Integer nu,Integer a,Integer c,Float f,Integer t) {
		setId(i);
		setNombre(no);
		setPais(p);
		setCiudad(ci);
		setCalle(ca);
		setNumero(nu);
		setAnio(a);
		setCapacidad(c);
		setSocios(null);
		setFinanciacion(f);
		setNum_trabajadores(t);
	}
	
	public RegistroTabla() {}
	
	// Metodos getter y setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer a) {
		this.anio = a;
	}
	public Integer getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getSocios() {
		return socios;
	}
	public Float getFinanciacion() {
		return financiacion;
	}
	public void setFinanciacion(Float financiacion) {
		this.financiacion = financiacion;
	}
	public void setSocios(Integer socios) {
		this.socios = socios;
	}
	public Integer getNum_trabajadores() {
		return num_trabajadores;
	}
	public void setNum_trabajadores(Integer num_trabajadores) {
		this.num_trabajadores = num_trabajadores;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	// un aeropuerto será el mismo cuando tenga el mismo id.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroTabla other = (RegistroTabla) obj;
		return id == other.id;
	}
	
	@Override
	public String toString() {
		return "RegistroTabla [id=" + id + ", capacidad=" + capacidad + ", numero=" + numero + ", anio=" + anio
				+ ", socios=" + socios + ", financiacion=" + financiacion + ", num_trabajadores=" + num_trabajadores
				+ ", nombre=" + nombre + ", pais=" + pais + ", ciudad=" + ciudad + ", calle=" + calle + "]";
	}
}
