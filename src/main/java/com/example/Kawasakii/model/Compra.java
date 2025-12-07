package com.example.Kawasakii.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "compras")
@Data // Genera todos los getters, setters, toString, equals, hashCode
@NoArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompra = new Date();

    private double total;

    // ✅ CAMPO AÑADIDO: Necesario para el flujo de pago
    private String metodoPago;

    // Relación con los detalles del carrito (ItemCarrito)
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude // Evita errores de LazyInitializationException
    private List<ItemCarrito> items;


}