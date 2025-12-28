package com.almacen.almacen.repositories;

import com.almacen.almacen.models.entiy.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
