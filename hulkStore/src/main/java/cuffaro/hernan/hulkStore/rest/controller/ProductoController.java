package cuffaro.hernan.hulkStore.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.service.ProductoService;

@RestController
@RequestMapping("api")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/productos")
    private List<Producto> getAllProductos() {
        return productoService.getAllPersons();
    }

    @GetMapping("/productos/{id}")
    private Producto getPerson(@PathVariable("id") long id) {
        return productoService.getProductoById(id);
    }

    @DeleteMapping("/productos/{id}")
    private void deleteProducto(@PathVariable("id") long id) {
        productoService.delete(id);
    }

    @PostMapping("/productos")
    private Long saveProducto(@RequestBody Producto producto) {
        productoService.saveOrUpdate(producto);
        return producto.getId();
    }
}