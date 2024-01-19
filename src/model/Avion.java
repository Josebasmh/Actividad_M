package model;

import java.util.Objects;

public class Avion {

	private int id;
	private String modelo;
	private Integer numero_asiento,velocidad_maxima,id_aeropuerto;
	Boolean activado;
	
	/**
	 * Modelo para crear y administrar aviones.
	 * @param i id
	 * @param m modelo
	 * @param n numero_asiento
	 * @param v velocidad_maxima
	 * @param ia id_aeropuerto
	 * @param a activado
	 */
	public Avion(int i, String m,Integer n,Integer v,Integer ia,Boolean a) {
		id=i;
		modelo=m;
		numero_asiento=n;
		velocidad_maxima=v;
		id_aeropuerto=ia;
		activado=a;
	}

	// Métodos getter y setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getNumero_asiento() {
		return numero_asiento;
	}

	public void setNumero_asiento(Integer numero_asiento) {
		this.numero_asiento = numero_asiento;
	}

	public Integer getVelocidad_maxima() {
		return velocidad_maxima;
	}

	public void setVelocidad_maxima(Integer velocidad_maxima) {
		this.velocidad_maxima = velocidad_maxima;
	}

	public Integer getId_aeropuerto() {
		return id_aeropuerto;
	}

	public void setId_aeropuerto(Integer id_aeropuerto) {
		this.id_aeropuerto = id_aeropuerto;
	}

	public Boolean getActivado() {
		return activado;
	}

	public void setActivado(Boolean activado) {
		this.activado = activado;
	}

	// Método hashcode y equals (id)
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avion other = (Avion) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		String txt="";
		if (activado) {
			txt="Activado";
		}else {
			txt="Desactivado";
		}
		return "   Modelo: " + modelo + "\n   Número de asientos: " + numero_asiento + "\n   Velocidad máxima: "
				+ velocidad_maxima + "\n   " + txt+"\n";
	}
}
