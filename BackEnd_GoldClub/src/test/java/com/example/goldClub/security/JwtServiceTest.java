package com.example.goldClub.security;

import com.example.goldClub.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private Claims claims;

    private String secret = "thisisaverylongsecretkeyforjwtwhichneedstobesecure"; // Clave segura para pruebas

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Configura el secret directamente en el JwtService
        jwtService.setSecret(secret);
    }

    @Test
    public void testGenerateToken() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");

        String token = jwtService.generateToken(usuario);

        assertEquals("test@example.com", jwtService.extractUsername(token));
    }

    @Test
    public void testIsTokenValid() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        String token = jwtService.generateToken(usuario);

        // Para `isTokenValid`, no necesitas mockear `jwtService` sino que debes crear un token válido y verificar su validez.
        Date expirationDate = jwtService.extractExpiration(token); // Usa el método real para obtener la fecha de expiración.

        // Simula la extracción de valores esperados
        // No es necesario mockear `jwtService` aquí ya que estamos probando el método real.
        boolean isValid = jwtService.isTokenValid(token);

        assertEquals(true, isValid);
    }

    @Test
    public void testExtractClaim() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        String token = jwtService.generateToken(usuario);

        // En el caso de `extractClaim`, simplemente usa el método real.
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = jwtService.extractClaim(token, Claims::getSubject);

        assertEquals("test@example.com", username);
    }
}
