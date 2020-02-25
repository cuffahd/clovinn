package cuffaro.hernan.hulkStore.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class MovimientoInventarioTest {

	public static final Long ID_ONE = 1L;
	
	@Test
	public void test_date() {
		MovimientoInventario mov = new MovimientoInventario();
		Assert.assertNotNull(mov.getFecha());
	}
	
	@Test
	public void test_validarCamposMinimos() {
		MovimientoInventario mov = new MovimientoInventario();
		Producto p = new Producto();
		Assert.assertFalse(mov.validarCamposMinimos());
		mov.setProducto(p);
		Assert.assertFalse(mov.validarCamposMinimos());
		p.setId(1L);
		mov.setProducto(p);
		Assert.assertFalse(mov.validarCamposMinimos());
		mov.setOperacion(TipoOperacionEnum.ADICION);
		Assert.assertFalse(mov.validarCamposMinimos());
		mov.setCantidad(-1);
		Assert.assertFalse(mov.validarCamposMinimos());
		mov.setCantidad(10);
		Assert.assertTrue(mov.validarCamposMinimos());
		}
	
	@Test
	public void test_accessor() {
		MovimientoInventario mov = new MovimientoInventario();
		Producto p = new Producto();
		mov.setId(ID_ONE);
		Assert.assertNotNull(mov.getId());
		Assert.assertEquals(ID_ONE, mov.getId());
		Date fecha = new Date();
		mov.setFecha(fecha);
		Assert.assertNotNull(mov.getFecha());
		Assert.assertEquals(fecha, mov.getFecha());
		mov.setCantidad(1);
		Assert.assertNotNull(mov.getCantidad());
		mov.setOperacion(TipoOperacionEnum.ADICION);
		Assert.assertNotNull(mov.getOperacion());
		Assert.assertEquals(TipoOperacionEnum.ADICION, mov.getOperacion());
		mov.setProducto(p);
		Assert.assertNotNull(mov.getProducto());
		Assert.assertEquals(p,  mov.getProducto());
	}
	
}
