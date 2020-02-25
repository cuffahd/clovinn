package cuffaro.hernan.hulkStore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.MovimientoInventario;
import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.model.TipoOperacionEnum;
import cuffaro.hernan.hulkStore.repository.IMovimientoInventarioDao;

@Service
public class MovimientoInventarioService implements IMovimientoInventarioService{

    @Autowired
    IMovimientoInventarioDao movimientoInventarioDao;

    @Autowired
    IProductoService productoService;

    @Override
    @Transactional
    public void saveOrUpdate(MovimientoInventario movimientoInventario) throws ServiceException{
        try {
        	movimientoInventarioDao.save(movimientoInventario);
			productoService.modificarStock(movimientoInventario.getCantidad(), movimientoInventario.getProducto(), movimientoInventario.getOperacion());
		} catch (ServiceException e) {
			throw e;
		}
    }

	@Override
	public List<MovimientoInventario> getAllMovimientosInventario() {
        List<MovimientoInventario> movimientoInventarioLista = new ArrayList<MovimientoInventario>();
        Iterable<MovimientoInventario> iterable = movimientoInventarioDao.findAll();
        for (MovimientoInventario movimientoInventario : iterable) {
        	movimientoInventarioLista.add(movimientoInventario);
		}
        return movimientoInventarioLista;
	}

	@Override
	public void validarOperacion(MovimientoInventario movimientoInventario) throws ServiceException {
		if(!movimientoInventario.validarCamposMinimos()) {
			throw new ServiceException("No se cumple con los campos minimos requeridos");
		}
		
		Producto bdProducto = null;
		try {
			bdProducto = productoService.getProductoById(movimientoInventario.getProducto().getId());
		} catch (ServiceException e) {
			throw new ServiceException("No Existe producto con el ID especificado");
		}
		
		if( (bdProducto.getStock() - movimientoInventario.getCantidad() <=-1) && TipoOperacionEnum.RETIRO.equals(movimientoInventario.getOperacion())) {
			throw new ServiceException("El stock a retirar es mayor del disponible");
		}
	}
}
