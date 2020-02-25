package cuffaro.hernan.hulkStore.model;

import org.junit.Assert;
import org.junit.Test;

public class ProductoTest {

	public static final Long ID_ONE = 1L;
	
	@Test
	public void test_hashCode() {
		Categoria categoria_1 = new Categoria(ID_ONE);
		Producto producto_1 = new Producto();
		Assert.assertEquals(29791 , producto_1.hashCode());
		producto_1.setCategoria(categoria_1);
		Assert.assertEquals(983103, producto_1.hashCode());
		producto_1.setId(ID_ONE);
		Assert.assertEquals(983134, producto_1.hashCode());
		producto_1.setNombre("nombre");
		Assert.assertEquals(-1038921689, producto_1.hashCode());
	}
	
	@Test
	public void validarCamposMinimos_true() {
		Categoria categoria_1 = new Categoria(ID_ONE);
		Producto producto_1 = new Producto();
		producto_1.setCategoria(categoria_1);
		producto_1.setNombre("nombre");
		Assert.assertTrue(producto_1.validarCamposMinimos());
	}
	
	@Test
	public void validarCamposMinimos_false() {
		Categoria categoria_1 = new Categoria();
		Producto producto_1 = new Producto();
		Assert.assertFalse(producto_1.validarCamposMinimos());
		producto_1.setNombre("nombre");
		Assert.assertFalse(producto_1.validarCamposMinimos());
		producto_1.setCategoria(categoria_1);
		Assert.assertFalse(producto_1.validarCamposMinimos());
	}

	@Test
	public void modificarStock_Adicion() {
		Producto producto_1 = new Producto();
		producto_1.setStock(100L);
		producto_1.modificarStock(10L, TipoOperacionEnum.ADICION);
		Assert.assertEquals(new Long(110), producto_1.getStock());
	}
	
	@Test
	public void modificarStock_Retiro() {
		Producto producto_1 = new Producto();
		producto_1.setStock(100L);
		producto_1.modificarStock(10L, TipoOperacionEnum.RETIRO);
		Assert.assertEquals(new Long(90), producto_1.getStock());
	}
	
	@Test
	public void test_equals() {
		Producto producto_1 = new Producto();
		Producto producto_2 = new Producto();
		Categoria cate = new Categoria(ID_ONE);
		Assert.assertTrue(producto_1.equals(producto_1));
		Assert.assertFalse(producto_1.equals(null));
		Assert.assertTrue(producto_2.equals(producto_1));
		Assert.assertFalse(producto_1.equals(ID_ONE));
		Assert.assertTrue(producto_1.equals(producto_2));
		Assert.assertTrue(producto_2.equals(producto_1));
		producto_2.setId(2L);
		Assert.assertFalse(producto_2.equals(producto_1));
		Assert.assertFalse(producto_1.equals(producto_2));
		producto_2.setId(ID_ONE);
		Assert.assertFalse(producto_2.equals(producto_1));
		Assert.assertFalse(producto_1.equals(producto_2));
		producto_1.setCategoria(cate);
		Assert.assertFalse(producto_2.equals(producto_1));
		Assert.assertFalse(producto_1.equals(producto_2));
		producto_2.setCategoria(new Categoria(2L));
		Assert.assertFalse(producto_2.equals(producto_1));
		Assert.assertFalse(producto_1.equals(producto_2));
		producto_1.setNombre("nombre");
		Assert.assertFalse(producto_2.equals(producto_1));
		Assert.assertFalse(producto_1.equals(producto_2));
		producto_1.setNombre(null);
		producto_2.setNombre(null);
		Assert.assertFalse(producto_2.equals(producto_1));
		Assert.assertFalse(producto_1.equals(producto_2));
		
	}
	
	@Test
	public void test_Accesors() {
		Producto p = new Producto();
		p.setId(ID_ONE);
		Assert.assertNotNull(p.getId());
		Assert.assertEquals(ID_ONE, p.getId());
		p.setStock(ID_ONE);
		Assert.assertNotNull(p.getStock());
		Assert.assertEquals(ID_ONE, p.getStock());
		p.setDescripcion("descripcion");
		Assert.assertNotNull(p.getDescripcion());
		Assert.assertEquals("descripcion", p.getDescripcion());
		p.setNombre("nombre");
		Assert.assertNotNull(p.getNombre());
		Assert.assertEquals("nombre", p.getNombre());
		p.setMarca("marca");
		Assert.assertNotNull(p.getMarca());
		Assert.assertEquals("marca", p.getMarca());
		Categoria cate = new Categoria();
		p.setCategoria(cate);
		Assert.assertNotNull(p.getCategoria());
		Assert.assertEquals(cate, p.getCategoria());
	}

	
}
