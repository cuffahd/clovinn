package cuffaro.hernan.hulkStore.repository;

import org.springframework.data.repository.CrudRepository;

import cuffaro.hernan.hulkStore.model.MovimientoInventario;

/**
 * 
 * DAO Movimiento inventario
 * 
 * @author hernan.d.cuffaro
 *
 */
public interface IMovimientoInventarioDao extends CrudRepository<MovimientoInventario, Long> {

}
