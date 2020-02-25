package cuffaro.hernan.hulkStore.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.MovimientoInventario;
import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.model.TipoOperacionEnum;
import cuffaro.hernan.hulkStore.repository.IMovimientoInventarioDao;

public class MovimientoInventarioServiceTest {

	@Mock
	IMovimientoInventarioDao movimientoInventarioDao;
	    
	@Mock
	IProductoService productoService;
	
	@InjectMocks
	MovimientoInventarioService movimientoInventarioService;
	
	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	}
	
	
	@Test(expected = ServiceException.class)
	public void test_validarOperacion() throws ServiceException {
		MovimientoInventario mov = new MovimientoInventario();
		movimientoInventarioService.validarOperacion(mov);
		Assert.fail();
	    
	}
	
	@Test(expected = ServiceException.class)
	public void test_validarOperacion_testException() throws ServiceException {
		Producto p = new Producto();
		p.setId(10L);
		p.setStock(20L);
		MovimientoInventario mov = new MovimientoInventario();
		mov.setProducto(p);
		mov.setOperacion(TipoOperacionEnum.ADICION);
		mov.setCantidad(10);
		Mockito.when(productoService.getProductoById(Mockito.anyLong())).thenThrow(new ServiceException());
		movimientoInventarioService.validarOperacion(mov);
		Assert.fail();
	    
	}
	
	@Test(expected = ServiceException.class)
	public void test_validarOperacion_testException_stock() throws ServiceException {
		Producto p = new Producto();
		p.setId(10L);
		p.setStock(2L);
		MovimientoInventario mov = new MovimientoInventario();
		mov.setProducto(p);
		mov.setOperacion(TipoOperacionEnum.RETIRO);
		mov.setCantidad(10);
		Mockito.when(productoService.getProductoById(Mockito.anyLong())).thenReturn(p);
		movimientoInventarioService.validarOperacion(mov);
		Assert.fail();
	}
	

}
