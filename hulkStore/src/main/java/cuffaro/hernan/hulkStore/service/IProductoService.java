package cuffaro.hernan.hulkStore.service;

import java.util.List;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.model.TipoOperacionEnum;

public interface IProductoService{

	List<Producto> getAllProductos();

	Producto getProductoById(long id) throws ServiceException;

	void delete(long id) throws ServiceException;

	Producto saveOrUpdate(Producto producto) throws ServiceException;

	void deleteProductByCategoria(long id);

	boolean validarCampos(Producto producto) throws ServiceException;

	boolean existeProducto(Producto producto);

	Producto modificarStock(Integer cantidad, Producto producto, TipoOperacionEnum operacion) throws ServiceException;

}
