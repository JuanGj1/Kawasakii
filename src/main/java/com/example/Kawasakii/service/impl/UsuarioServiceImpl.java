package com.example.Kawasakii.service.impl;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.model.Usuario;
import com.example.Kawasakii.repository.ProductoRepository;
import com.example.Kawasakii.repository.UsuarioRepository;
import com.example.Kawasakii.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ProductoRepository productoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario registrarUsuario(String username, String password) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setRoles("USER");
        return usuarioRepository.save(u);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
    }

    @Override
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public void agregarFavorito(String username, Long productoId) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
        usuario.getFavoritos().add(producto);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void quitarFavorito(String username, Long productoId) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
        usuario.getFavoritos().remove(producto);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerFavoritosDeUsuario(String username) {
        Optional<Usuario> opt = usuarioRepository.findByUsername(username);
        if (opt.isEmpty()) return new ArrayList<>();
        Usuario usuario = opt.get();
        return new ArrayList<>(usuario.getFavoritos());
    }
}

