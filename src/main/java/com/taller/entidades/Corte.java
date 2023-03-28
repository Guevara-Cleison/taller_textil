package com.taller.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tb_cortes")
public class Corte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCorte;

	@ManyToOne
	@JoinColumn(name = "idEmpresa")
	@NotNull(message = "Debe ingresar empresa")
	private Empresa idEmpresa;

	@Min(value = 1, message = "Debe ingresar op")
	private int op;

	@Min(value = 1, message = "Debe ingresar os")
	private int os;

	@NotEmpty(message = "Debe ingresar modelo")
	private String modelo;

	@Min(value = 1, message = "Debe ingresar cantidad")
	private int cantidad;

	@DecimalMin(value = "0.1", message = "Debe ingresar precio")
	private double precio;

	@NotBlank(message = "Debe ingresar numero de Guía")
	private String numGui;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Debe ingresar fecha de entrega")
	private Date fecEntrega;

	public Corte() {
	}

	public Corte(int idCorte, Empresa idEmpresa, int op, int os, String modelo, int cantidad, double precio, String numGui,
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

	public Empresa getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Empresa idEmpresa) {
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getNumGui() {
		return numGui;
	}

	public void setNumGui(String numGui) {
		this.numGui = numGui;
	}

	public Date getFecEntrega() {
		return fecEntrega;
	}

	public void setFecEntrega(Date fecEntrega) {
		this.fecEntrega = fecEntrega;
	}

}
