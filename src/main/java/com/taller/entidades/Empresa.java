package com.taller.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_empresas")
public class Empresa {

	@Id
	@NotNull
	private Long idEmpresa;

	@NotEmpty
	private String descripcion;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	private String direccion;

	public Empresa() {
	}

	public Empresa(Long idEmpresa, String descripcion, String email, String direccion) {
		this.idEmpresa = idEmpresa;
		this.descripcion = descripcion;
		this.email = email;
		this.direccion = direccion;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
