package com.almacen.almacen.models;

import com.almacen.almacen.service.pedido.IPedido;
import lombok.Data;

import java.util.List;

@Data
public class RequestValidaciones {

    private int cliente;
    private int folio;
    private int fitir;
    List<IPedido> pedidoList;
}
