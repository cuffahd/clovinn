package cuffaro.hernan.hulkStore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import cuffaro.hernan.hulkStore.utils.IteratorUtilsTest;

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
	
	@Test(expected = ServiceException.class)
	public void test_save() throws ServiceException {
		MovimientoInventario mi = new MovimientoInventario();
		Mockito.when(movimientoInventarioDao.save(Mockito.any())).thenReturn(mi);
		Mockito.when(productoService.modificarStock(
				Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new ServiceException());
		movimientoInventarioService.saveOrUpdate(mi);
		Assert.fail();
	}
	
	@Test
	public void test_getAll() {
		MovimientoInventario mov = new MovimientoInventario();
		mov.setCantidad(1);
		mov.setFecha(new Date());
		mov.setId(1L);
		mov.setOperacion(TipoOperacionEnum.ADICION);
		mov.setProducto(new Producto());
		
		List<MovimientoInventario> movList = new ArrayList<MovimientoInventario>();
		
		movList.add(mov);
		Iterable<MovimientoInventario> movIte =  IteratorUtilsTest.iteratorToIterable(movList.iterator());
		
		Mockito.when(movimientoInventarioDao.findAll()).thenReturn(movIte);
		
		List<MovimientoInventario> returnedList = movimientoInventarioService.getAllMovimientosInventario();
		Assert.assertEquals(1, returnedList.size());
		Assert.assertEquals(movList, returnedList);
	}
}
