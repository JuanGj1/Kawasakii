package com.example.Kawasakii.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "items_carrito")
@Data
@NoArgsConstructor
public class ItemCarrito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id")
    @ToString.Exclude
    private Compra compra;

    private int cantidad;
    private double subtotal;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = (producto != null) ? producto.getPrecio() * cantidad : 0.0;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        if (this.producto != null) {
            this.subtotal = this.producto.getPrecio() * this.cantidad;
        }
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (this.producto != null) {
            this.subtotal = this.producto.getPrecio() * this.cantidad;
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public Long getId() {
        return id;
    }

    public Compra getCompra() {
        return compra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }
    public interface ItemCarritoService {
        ItemCarrito guardar(ItemCarrito item);
    }
}