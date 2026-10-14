package com.sistemas.service;

import java.util.List;
import java.util.Optional;

import com.sistemas.model.Usuario;

public interface UsuarioService {
	 List<Usuario> listarTodos();
	    Optional<Usuario> buscarPorId(Long id);
	    Optional<Usuario> buscarPorEmail(String email);
	    Usuario guardar(Usuario usuario);
	    void eliminar(Long id);
}
