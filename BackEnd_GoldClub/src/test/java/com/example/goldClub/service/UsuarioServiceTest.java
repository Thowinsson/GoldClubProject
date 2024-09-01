package com.example.goldClub.service;

import com.example.goldClub.models.Usuario;
import com.example.goldClub.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    public UsuarioServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setPassword("password");

        when(passwordEncoder.encode(usuario.getPassword())).thenReturn("encodedPassword");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.registrarUsuario(usuario);

        assertEquals("encodedPassword", result.getPassword());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void testBuscarPorEmail() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.buscarPorEmail(email);

        assertEquals(Optional.of(usuario), result);
        verify(usuarioRepository).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String email = "test@example.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> usuarioService.loadUserByUsername(email));
    }
}
