package com.sistemas.dto;

import com.sistemas.model.Rol;

public class RegistroRequest {

	    private String nombre;
	    private String email;
	    private String password;
	    private String confirmarPassword;
	    private Rol rol;

	    public RegistroRequest() {}

	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getPassword() { return password; }
	    public void setPassword(String password) { this.password = password; }

	    public String getConfirmarPassword() {
			return confirmarPassword;
		}

		public void setConfirmarPassword(String confirmarPassword) {
			this.confirmarPassword = confirmarPassword;
		}

		public Rol getRol() { return rol; }
	    public void setRol(Rol rol) { this.rol = rol; }
	

}
