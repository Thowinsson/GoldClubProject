package com.example.goldClub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.goldClub.models.Empleado;
import com.example.goldClub.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    // Inyección del repositorio de empleados
    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Método para listar todos los empleados
    public List<Empleado> listarEmpleados() {
        // Usar el repositorio para obtener todos los empleados
        return empleadoRepository.findAll();
    }

    // Método para guardar un nuevo empleado
    public Empleado guardarEmpleado(Empleado empleado) {
        // Guardar el empleado en la base de datos
        return empleadoRepository.save(empleado);
    }

    // Método para buscar un empleado por su código
    public Optional<Empleado> buscarPorCodigoEmpleado(int codigoEmpleado) {
        // Buscar empleado por su código en la base de datos
        return empleadoRepository.findByCodigoEmpleado(codigoEmpleado);
    }

    // Método para eliminar un empleado por su código
    public void eliminarEmpleado(int codigoEmpleado) {
        // Eliminar empleado usando su código
        empleadoRepository.deleteById(codigoEmpleado);
    }
}
