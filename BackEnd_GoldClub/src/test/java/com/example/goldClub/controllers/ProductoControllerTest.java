package com.example.goldClub.controllers;

import com.example.goldClub.models.Producto;
import com.example.goldClub.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    public ProductoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarProductos() {
        Producto producto = new Producto();
        List<Producto> productos = Collections.singletonList(producto);

        when(productoService.listarProductos()).thenReturn(productos);

        ResponseEntity<List<Producto>> response = productoController.listarProductos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productos, response.getBody());
    }

    @Test
    public void testGuardarProducto() {
        Producto producto = new Producto();

        when(productoService.guardarProducto(producto)).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.guardarProducto(producto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(producto, response.getBody());
    }

    @Test
    public void testEliminarProducto() {
        Long id = 1L;

        doNothing().when(productoService).eliminarProducto(id);

        ResponseEntity<String> response = productoController.eliminarProducto(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Producto eliminado exitosamente", response.getBody());
    }
}
