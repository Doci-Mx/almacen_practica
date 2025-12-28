package com.almacen.almacen.controller;

import com.almacen.almacen.config.annotation.TimedExecution;
import com.almacen.almacen.models.entiy.Producto;
import com.almacen.almacen.service.producto.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("api/productos")
@RestController
public class ProductoController {

    @Autowired
    IProductoService service;


    @TimedExecution
    @GetMapping
    public Set<Producto> finAll(){
        return service.getAllProductos();
    }

    @GetMapping("/{id}")
    public Producto findById(@PathVariable Long id){
        return service.getProductoById(id);
    }

    @PostMapping
    public String save(@RequestBody Producto producto){
        return service.save(producto);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id,@RequestBody Producto producto){
        return service.update(producto, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        return service.deleteById(id);
    }
}
