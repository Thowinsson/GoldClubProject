package com.example.goldClub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goldClub.models.Producto;
import com.example.goldClub.repository.ProductoRepository;

@Service
public class ProductoService {

    // Inyección del repositorio de productos
    @Autowired
    private ProductoRepository productoRepository;

    // Método para listar todos los productos
    public List<Producto> listarProductos() {
        // Obtener todos los productos de la base de datos
        return productoRepository.findAll();
    }

    // Método para guardar un nuevo producto
    public Producto guardarProducto(Producto producto) {
        // Guardar el producto en la base de datos
        return productoRepository.save(producto);
    }

    // Método para eliminar un producto por su ID
    public void eliminarProducto(Long id) {
        // Eliminar el producto de la base de datos usando su ID
        productoRepository.deleteById(id);
    }
}
