package cuffaro.hernan.hulkStore.service;

import java.util.List;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.MovimientoInventario;

public interface IMovimientoInventarioService {

	List<MovimientoInventario> getAllMovimientosInventario();

	void saveOrUpdate(MovimientoInventario movimientoInventario) throws ServiceException;

	void validarOperacion(MovimientoInventario movimientoInventario) throws ServiceException;

}
