package com.almacen.almacen.service.producto;

import com.almacen.almacen.models.entiy.Producto;
import com.almacen.almacen.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    ProductoRepository repository;

    ProductoServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProductoServiceImpl(repository);
    }

    @Test
    void getAllProductos_returnsSet() {
        Producto p1 = new Producto(); p1.setId(1L);
        Producto p2 = new Producto(); p2.setId(2L);
        when(repository.findAll()).thenReturn(List.of(p1, p2));

        Set<Producto> result = service.getAllProductos();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getProductoById_present() {
        Producto p = new Producto();
        p.setId(1L);
        p.setNombre("Test");
        when(repository.findById(1L)).thenReturn(Optional.of(p));

        Producto res = service.getProductoById(1L);

        assertNotNull(res);
        assertEquals("Test", res.getNombre());
        verify(repository).findById(1L);
    }

    @Test
    void getProductoById_absent_returnsNewProducto() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Producto res = service.getProductoById(1L);

        assertNotNull(res);
        // objeto vacío esperado
        verify(repository).findById(1L);
    }

    @Test
    void save_success_returnsSuccessMessage() {
        Producto p = new Producto();
        p.setNombre("Nuevo");
        when(repository.save(any(Producto.class))).thenReturn(p);

        String msg = service.save(p);

        assertEquals("Producto guardado con exito", msg);
        verify(repository).save(p);
    }

    @Test
    void save_exception_returnsErrorMessage() {
        Producto p = new Producto();
        when(repository.save(any(Producto.class))).thenThrow(new RuntimeException("DB error"));

        String msg = service.save(p);

        assertEquals("Error al guardar el producto", msg);
        verify(repository).save(p);
    }

    @Test
    void update_exists_updatesAndReturnsSuccess() {
        Producto db = new Producto();
        db.setId(1L);
        db.setNombre("Old");
        when(repository.findById(1L)).thenReturn(Optional.of(db));
        Producto incoming = new Producto();
        incoming.setNombre("New");
        incoming.setDescripcion("Desc");
        incoming.setPrecio(12.5);
        incoming.setCantidad(3);

        String msg = service.update(incoming, 1L);

        assertEquals("Producto actualizado con exito", msg);
        verify(repository).findById(1L);
        verify(repository).save(argThat((Producto saved) ->
                "New".equals(saved.getNombre()) &&
                        "Desc".equals(saved.getDescripcion()) &&
                        Double.valueOf(12.5).equals(saved.getPrecio()) &&
                        Integer.valueOf(3).equals(saved.getCantidad())
        ));
    }

    @Test
    void update_notExists_returnsNotFoundMessage() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        Producto incoming = new Producto();

        String msg = service.update(incoming, 2L);

        assertEquals("El producto no existe", msg);
        verify(repository).findById(2L);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteById_callsRepositoryDelete() {
        doNothing().when(repository).deleteById(1L);

        String msg = service.deleteById(1L);

        assertTrue(msg.contains("1"));
        assertTrue(msg.contains("eliminado con exito"));
        verify(repository).deleteById(1L);
    }
}