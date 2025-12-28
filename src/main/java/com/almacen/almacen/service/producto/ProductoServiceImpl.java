package com.almacen.almacen.service.producto;

import com.almacen.almacen.config.annotation.TimedExecution;
import com.almacen.almacen.models.Pedido;
import com.almacen.almacen.models.PedidoLam;
import com.almacen.almacen.models.RequestValidaciones;
import com.almacen.almacen.models.entiy.Producto;
import com.almacen.almacen.repositories.ProductoRepository;
import static com.almacen.almacen.utils.Constantes.REQUEST;

import com.almacen.almacen.service.pedido.IPedido;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class ProductoServiceImpl implements IProductoService{



    ProductoRepository repository;

    private boolean lam = true;

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

    @Override
    public RequestValidaciones getPedidos(){
        RequestValidaciones req = new RequestValidaciones();
        req.setCliente(1);
        req.setFolio(123);
        req.setFitir(456);

        List<IPedido> pedidoLamList = new ArrayList<>(
                Arrays.asList(
                        new PedidoLam(1, 2, "Producto A"),
                        new PedidoLam(2, 3, "Producto B"),
                        new PedidoLam(3, 4, "Producto C")
                )
        );

        List<IPedido> pedidoList = new ArrayList<>(
                Arrays.asList(
                        new Pedido(1),
                        new Pedido(2),
                        new Pedido(3
                )
        ));


        if (!lam) {
            req.setPedidoList(pedidoLamList);
        } else {
            req.setPedidoList(pedidoList);
        }

        log.info("Response " + req);
        return req;
    }
}
