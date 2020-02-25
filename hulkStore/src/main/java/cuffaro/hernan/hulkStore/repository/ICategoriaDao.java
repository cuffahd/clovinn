package cuffaro.hernan.hulkStore.repository;

import org.springframework.data.repository.CrudRepository;

import cuffaro.hernan.hulkStore.model.Categoria;

/**
 * DAO Categoria
 * 
 * @author hernan.d.cuffaro
 *
 */
public interface ICategoriaDao extends CrudRepository<Categoria, Long> {

}
