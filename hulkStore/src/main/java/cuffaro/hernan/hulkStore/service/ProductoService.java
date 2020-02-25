package cuffaro.hernan.hulkStore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Categoria;
import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.model.TipoOperacionEnum;
import cuffaro.hernan.hulkStore.repository.IProductoDao;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    IProductoDao productoDao;
    
    @Autowired
    ICategoriaService categoriaService;

    private static final Logger logger = LogManager.getLogger(ProductoService.class);
    
    public Producto getProductoById(long id) throws ServiceException {
        try {
        	 return productoDao.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ServiceException("No existe producto con ID: " + id, e);
		}
    }

    public Producto saveOrUpdate(Producto producto) throws ServiceException {
        try {
        	return productoDao.save(producto);
		} catch(DataIntegrityViolationException e) {
			throw new ServiceException("Ya existe un producto con este nombre: " + producto.getNombre(), e);
		} 
    }

    public void delete(long id) throws ServiceException {
        try {
        	 productoDao.deleteById(id);	
		} catch (EmptyResultDataAccessException e) {
			logger.info("No existia producto con id: " +id);
		}catch(Exception e) {
			throw new ServiceException("Hubo un error procesando el delete",e);
		}
    }

	@Override
	@Transactional
	public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<Producto>();
        Iterable<Producto> iterable =productoDao.findAll();
        for (Producto producto : iterable) {
        	productos.add(producto);
		}
        return productos;
	}

	@Override
	public void deleteProductByCategoria(long id) {
		try {
			productoDao.deleteByCategoria(new Categoria(id));
		} catch (EmptyResultDataAccessException e) {
			logger.info("No habia ningun producto asociado a categoria " + id);
		}
	}

	/**
	 * Valida los campos minimos para que un Producto sea Valido y ademas que la categoria asociada al producto Exista.
	 */
	@Override
	public boolean validarCampos(Producto producto) throws ServiceException {
		if(!producto.validarCamposMinimos()) {
			//No se cumple con los campos minimos requeridos.
			throw new ServiceException("Campos requeridos incompletos");
		}else if(!categoriaService.existeCategoria(producto.getCategoria()) ) {
			throw new ServiceException("Categoria inexistente");
		}
		return true;
	}

	/**
	 * Valida que el producto exista actualmente en la BD
	 */
	@Override
	public boolean existeProducto(Producto producto) {
		boolean toReturn = true;
		try {
			Producto bdProducto = this.getProductoById(producto.getId());
			if(!bdProducto.equals(producto)) {
				toReturn = false;
			}
		} catch (ServiceException e) {
			toReturn = false;
		}
		return toReturn;
	}

	/**
	 * Modifica el stock del producto segun la operacion y guarda los cambios en la BD.
	 */
	@Override
	public Producto modificarStock(Integer cantidad, Producto producto, TipoOperacionEnum operacion) throws ServiceException {
		Producto bdProducto = this.getProductoById(producto.getId());
		bdProducto.modificarStock(Long.valueOf(cantidad), operacion);
		return this.saveOrUpdate(bdProducto);
	}
}
