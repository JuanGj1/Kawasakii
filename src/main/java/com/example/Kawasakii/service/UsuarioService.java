package com.example.Kawasakii.service;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario registrarUsuario(String username, String password);
    Usuario buscarPorUsername(String username);
    boolean existeUsername(String username);

    // Favoritos por usuario
    void agregarFavorito(String username, Long productoId);
    void quitarFavorito(String username, Long productoId);
    List<Producto> obtenerFavoritosDeUsuario(String username);
}

