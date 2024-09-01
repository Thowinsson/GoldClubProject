package com.example.goldClub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.goldClub.models.Usuario;
import com.example.goldClub.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    // Inyección del repositorio de usuarios
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Inyección del codificador de contraseñas
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para registrar un nuevo usuario
    public Usuario registrarUsuario(Usuario usuario) {
        // Codificar la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    // Método para buscar un usuario por su email
    public Optional<Usuario> buscarPorEmail(String email) {
        // Buscar usuario por email en la base de datos
        return usuarioRepository.findByEmail(email);
    }

    // Método requerido por UserDetailsService para cargar un usuario por su nombre de usuario (en este caso, el email)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));
        
        // Crear un objeto UserDetails utilizando los datos del usuario encontrado
        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles("USER") // Aquí se puede asignar roles específicos al usuario
                .build();
    }
}
