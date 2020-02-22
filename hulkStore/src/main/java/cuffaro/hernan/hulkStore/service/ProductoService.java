package cuffaro.hernan.hulkStore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.repository.IProductoDao;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    IProductoDao productoDao;

    public List<Producto> getAllPersons() {
        List<Producto> productos = new ArrayList<Producto>();
        productoDao.findAll().forEach(producto -> productos.add(producto));
        return productos;
    }

    public Producto getProductoById(long id) {
        return productoDao.findById(id).get();
    }

    public void saveOrUpdate(Producto person) {
        productoDao.save(person);
    }

    public void delete(long id) {
        productoDao.deleteById(id);
    }
}
