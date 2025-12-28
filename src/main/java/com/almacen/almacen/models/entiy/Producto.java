package com.almacen.almacen.models.entiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
    @SequenceGenerator(name = "producto_seq", sequenceName = "producto_seq", allocationSize = 1)
    private Long id;

    @Column
    @NotEmpty
    private String nombre;

    @Column
    @NotEmpty
    private String descripcion;

    @Column
    @NonNull
    private Double precio;

    @Column
    @Min(0)
    @NonNull
    private Integer cantidad;
}
