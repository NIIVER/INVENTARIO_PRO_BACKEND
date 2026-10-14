package com.sistemas.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.sistemas.dto.LoginRequest;
import com.sistemas.model.Usuario;
import com.sistemas.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200") 
public class AuthController {
	private final UsuarioRepository usuarioRepository;

    // Ya no inyectamos JwtUtil aquí 👍
    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).orElse(null);

        // Validar usuario y contraseña con jBCrypt directamente
        if (usuario == null || !BCrypt.checkpw(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
        }

        // Respondemos directamente con los datos que Angular necesita para su sesión visual
        return ResponseEntity.ok(Map.of(
            "id", usuario.getId(),
            "nombre", usuario.getNombre(),
            "rol", usuario.getRol().name(),
            "email", usuario.getEmail()
        ));
    }
}