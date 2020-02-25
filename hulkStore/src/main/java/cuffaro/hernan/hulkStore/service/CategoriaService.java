package cuffaro.hernan.hulkStore.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Categoria;
import cuffaro.hernan.hulkStore.repository.ICategoriaDao;

@Service
public class CategoriaService implements ICategoriaService{

    @Autowired
    ICategoriaDao categoriaDao;
    
    @Autowired
    IProductoService productoService;

    private static final Logger logger = LogManager.getLogger(CategoriaService.class);
    
    @Override
    public Categoria getCategoriaById(long id) throws ServiceException {
        try {
        	return categoriaDao.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ServiceException("No existe categoria con ID: " + id, e);
		}
    }

    @Override
    public void saveOrUpdate(Categoria categoria) throws ServiceException {
        try {
        	categoriaDao.save(categoria);
		} catch(DataIntegrityViolationException e) {
			throw new ServiceException("Ya existe una categoria con este nombre: " + categoria.getNombre(), e);
		} 
    }

    @Override
    @Transactional
    public void delete(long id) throws ServiceException {
		try {
			productoService.deleteProductByCategoria(id); 
			categoriaDao.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			logger.info("No existia la categoria con id: " +id);
		}catch(Exception e) {
			throw new ServiceException("Hubo un error procesando el delete",e);
		}
    }

	@Override
	public Set<Categoria> getAllCategorias() {
		  Set<Categoria> categorias = new HashSet<Categoria>();
	        Iterable<Categoria> iterable = categoriaDao.findAll();
	        for (Categoria categoria : iterable) {
				categorias.add(categoria);
			}
	        
	        return categorias;
	}

	@Override
	public boolean existeCategoria(Categoria categoria) {
		boolean toReturn = true;
		try {
			Categoria bdCategoria = this.getCategoriaById(categoria.getId());
			if(!bdCategoria.equals(categoria)) {
				toReturn = false;
			}
		} catch (ServiceException e) {
			toReturn = false;
		}
		return toReturn;
	}
}
