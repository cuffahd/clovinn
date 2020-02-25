package cuffaro.hernan.hulkStore.service;

import java.util.Set;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Categoria;

public interface ICategoriaService {

	Set<Categoria> getAllCategorias();

	Categoria getCategoriaById(long id) throws ServiceException;

	void saveOrUpdate(Categoria categoria) throws ServiceException;

	void delete(long id) throws ServiceException;

	boolean existeCategoria(Categoria categoria);

}
