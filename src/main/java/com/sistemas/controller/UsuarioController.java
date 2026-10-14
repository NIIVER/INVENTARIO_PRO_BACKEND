package com.sistemas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemas.model.Rol;
import com.sistemas.model.Usuario;
import com.sistemas.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200") // habilita Angular
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().build(); 
        }
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.EMPLEADO);
        }
        if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
            usuario.setPassword(hashedPassword);
        }
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    // 🟢 ACTUALIZAR POR ID (Para "Mi Perfil" y edición)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioData) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNombre(usuarioData.getNombre());
            usuarioExistente.setEmail(usuarioData.getEmail());
            
            if (usuarioData.getRol() != null) {
                usuarioExistente.setRol(usuarioData.getRol());
            }
            if (usuarioData.getPassword() != null && !usuarioData.getPassword().trim().isEmpty()) {
                String hashedPassword = BCrypt.hashpw(usuarioData.getPassword(), BCrypt.gensalt());
                usuarioExistente.setPassword(hashedPassword);
            }
            return ResponseEntity.ok(usuarioRepository.save(usuarioExistente));
        }).orElse(ResponseEntity.notFound().build());
    }
    // Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
        	
            return ResponseEntity.badRequest().build(); 
        }

        // Primer usuario será ADMIN, los demás EMPLEADO
        Rol rolAsignado = usuarioRepository.count() == 0 ? Rol.ADMIN : Rol.EMPLEADO;
        usuario.setRol(rolAsignado);

        // ✅ Encriptar contraseña con jBCrypt
        String hashedPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
        usuario.setPassword(hashedPassword);

        Usuario guardado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(guardado);
    }

    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(401).build(); // usuario no existe
        }
        boolean valid = BCrypt.checkpw(loginRequest.getPassword(), usuario.getPassword());
        if (!valid) {
            return ResponseEntity.status(401).build(); // contraseña incorrecta
        }

        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
