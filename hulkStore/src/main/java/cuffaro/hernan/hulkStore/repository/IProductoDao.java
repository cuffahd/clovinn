package cuffaro.hernan.hulkStore.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cuffaro.hernan.hulkStore.model.Categoria;
import cuffaro.hernan.hulkStore.model.Producto;

/**
 * DAO producto
 * 
 * @author hernan.d.cuffaro
 *
 */
public interface IProductoDao extends CrudRepository<Producto, Long> {


	/**
	 * Borra todos los productos asociados a una categoria.
	 * @param categoria
	 */
	@Modifying
	@Query("delete from producto p where p.categoria=:categoria")
	void deleteByCategoria(@Param("categoria") Categoria categoria);

	
}
