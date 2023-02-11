package com.taller.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tb_cortes")
public class Corte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCorte;

	@NotNull
	private Long idEmpresa;

	@NotNull
	private int op;

	@NotNull
	private int os;

	@NotEmpty
	private String modelo;

	@NotNull
	private int cantidad;

	@NotNull
	private int precio;

	@NotNull
	private int numGui;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecEntrega;

	public Corte() {
	}

	public Corte(int idCorte, Long idEmpresa, int op, int os, String modelo, int cantidad, int precio, int numGui,
			Date fecEntrega) {
		this.idCorte = idCorte;
		this.idEmpresa = idEmpresa;
		this.op = op;
		this.os = os;
		this.modelo = modelo;
		this.cantidad = cantidad;
		this.precio = precio;
		this.numGui = numGui;
		this.fecEntrega = fecEntrega;
	}

	public int getIdCorte() {
		return idCorte;
	}

	public void setIdCorte(int idCorte) {
		this.idCorte = idCorte;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
	}

	public int getOs() {
		return os;
	}

	public void setOs(int os) {
		this.os = os;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getNumGui() {
		return numGui;
	}

	public void setNumGui(int numGui) {
		this.numGui = numGui;
	}

	public Date getFecEntrega() {
		return fecEntrega;
	}

	public void setFecEntrega(Date fecEntrega) {
		this.fecEntrega = fecEntrega;
	}

}
