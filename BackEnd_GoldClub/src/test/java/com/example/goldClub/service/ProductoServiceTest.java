package com.example.goldClub.service;

import com.example.goldClub.models.Producto;
import com.example.goldClub.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    public ProductoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarProductos() {
        Producto producto = new Producto();
        List<Producto> productos = Collections.singletonList(producto);

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> result = productoService.listarProductos();

        assertEquals(productos, result);
        verify(productoRepository).findAll();
    }

    @Test
    public void testGuardarProducto() {
        Producto producto = new Producto();
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto result = productoService.guardarProducto(producto);

        assertEquals(producto, result);
        verify(productoRepository).save(producto);
    }

    @Test
    public void testEliminarProducto() {
        Long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        productoService.eliminarProducto(id);

        verify(productoRepository).deleteById(id);
    }
}
