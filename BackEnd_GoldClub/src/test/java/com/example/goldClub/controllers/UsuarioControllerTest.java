package com.example.goldClub.controllers;

import com.example.goldClub.models.Usuario;
import com.example.goldClub.models.Empleado;
import com.example.goldClub.security.JwtService;
import com.example.goldClub.service.UsuarioService;
import com.example.goldClub.service.EmpleadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EmpleadoService empleadoService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder; // Agrega el mock para PasswordEncoder

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarUsuario_Exitoso() {
        Usuario usuario = new Usuario();
        usuario.setCodigoEmpleado(123); // Configura un código de empleado válido
        Empleado empleado = new Empleado();
        
        when(empleadoService.buscarPorCodigoEmpleado(usuario.getCodigoEmpleado())).thenReturn(Optional.of(empleado));
        when(usuarioService.registrarUsuario(usuario)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.registrarUsuario(usuario);

        assertEquals(200, response.getStatusCodeValue(), "El código de estado HTTP debería ser 200");
        assertEquals(usuario, response.getBody(), "El cuerpo de la respuesta debería ser el usuario registrado");
    }

    @Test
    public void testLoginUsuario_Exitoso() {
        Usuario usuario = new Usuario();
        usuario.setEmail("julian@example.com");
        usuario.setPassword("123456789");
        String token = "testToken";
        
        when(usuarioService.buscarPorEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(usuario.getPassword(), usuario.getPassword())).thenReturn(true); // Mockea el método matches
        when(jwtService.generateToken(usuario)).thenReturn(token);

        ResponseEntity<String> response = usuarioController.loginUsuario(usuario);

        assertEquals(200, response.getStatusCodeValue(), "El código de estado HTTP debería ser 200");
        assertEquals(token, response.getBody(), "El cuerpo de la respuesta debería ser el token generado");
    }

    @Test
    public void testLoginUsuario_InvalidCredentials() {
        Usuario usuario = new Usuario();
        usuario.setEmail("julian@example.com");
        
        when(usuarioService.buscarPorEmail(usuario.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<String> response = usuarioController.loginUsuario(usuario);

        assertEquals(401, response.getStatusCodeValue(), "El código de estado HTTP debería ser 401");
        assertEquals("Credenciales incorrectas", response.getBody(), "El cuerpo de la respuesta debería ser el mensaje de error");
    }
}
