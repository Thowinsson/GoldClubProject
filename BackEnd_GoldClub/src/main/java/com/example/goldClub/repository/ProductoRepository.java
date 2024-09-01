package com.example.goldClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.goldClub.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}