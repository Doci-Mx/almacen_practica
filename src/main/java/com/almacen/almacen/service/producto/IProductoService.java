package com.almacen.almacen.service.producto;

import com.almacen.almacen.models.entiy.Producto;

import java.util.Set;

public interface IProductoService {

    Set<Producto> getAllProductos();

    Producto getProductoById(Long id);

    String save(Producto producto);

    String update(Producto producto, long id);

    String deleteById(Long id);
}
