package com.almacen.almacen.models;

import com.almacen.almacen.service.pedido.IPedido;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoLam implements IPedido {

    private int numeroPedido;
    private int cantidad;
    private String descripcion;
}
