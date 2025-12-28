package com.almacen.almacen.service.producto;

import com.almacen.almacen.config.annotation.TimedExecution;
import com.almacen.almacen.models.entiy.Producto;
import com.almacen.almacen.repositories.ProductoRepository;
import static com.almacen.almacen.utils.Constantes.REQUEST;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Log4j2
public class ProductoServiceImpl implements IProductoService{



    ProductoRepository repository;

    ProductoServiceImpl(ProductoRepository repository){
        this.repository = repository;
    }


    @Override
    @TimedExecution
    public Set<Producto> getAllProductos() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public Producto getProductoById(Long id) {
        log.info(REQUEST + " id " + id);
        Optional<Producto> productoDb= repository.findById(id);
        log.info("Response DB de producto: " + productoDb);
        return productoDb.orElseGet(Producto::new);
    }

    @Override
    public String save(Producto producto) {
        log.info(REQUEST + " Producto " + producto);
        try {
            Producto productoGuardado = repository.save(producto);
            log.info("Producto guardado: " + productoGuardado);
            return "Producto guardado con exito";
        } catch (Exception e) {
            log.error("Error al guardar el producto: " + e.getMessage());
            return "Error al guardar el producto";
        }
    }

    @Override
    public String update(Producto producto, long id) {
        Optional<Producto> productoDb = repository.findById(id);
        String message = "El producto no existe";
        if(productoDb.isPresent()){
            Producto productoUpdate = productoDb.get();
            productoUpdate.setNombre(producto.getNombre());
            productoUpdate.setDescripcion(producto.getDescripcion());
            productoUpdate.setPrecio(producto.getPrecio());
            productoUpdate.setCantidad(producto.getCantidad());
            repository.save(productoUpdate);

            message = "Producto actualizado con exito";
        }
        log.info("Request " + producto );
        log.info("Response " + productoDb);
        return message;
    }

    @Override
    public String deleteById(Long id) {
        repository.deleteById(id);
        return id + " eliminado con exito";
    }
}
