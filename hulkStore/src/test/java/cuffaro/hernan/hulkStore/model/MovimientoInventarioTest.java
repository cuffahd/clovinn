package cuffaro.hernan.hulkStore.model;

import org.junit.Assert;
import org.junit.Test;

public class MovimientoInventarioTest {

	
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
	
}
