package com.almacen.almacen.models;

import com.almacen.almacen.service.pedido.IPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido implements IPedido {

    private int numeroPedido;
}
