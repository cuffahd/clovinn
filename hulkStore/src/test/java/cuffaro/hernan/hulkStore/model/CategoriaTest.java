package cuffaro.hernan.hulkStore.model;

import org.junit.Assert;
import org.junit.Test;

public class CategoriaTest {

	public static final Long ID_ONE = 1L;
	
	@Test
	public void test_equals() {
		Categoria categoria_1 = new Categoria(ID_ONE);
		Categoria categoria_2 = new Categoria("nombre", "Descripcion");
		Assert.assertTrue(categoria_1.equals(categoria_1));
		Assert.assertFalse(categoria_1.equals(null));
		Assert.assertFalse(categoria_1.equals(ID_ONE));
		Assert.assertFalse(categoria_1.equals(categoria_2));
		Assert.assertFalse(categoria_2.equals(categoria_1));
		categoria_2.setId(2L);
		Assert.assertFalse(categoria_2.equals(categoria_1));
		categoria_2.setId(ID_ONE);
		Assert.assertFalse(categoria_2.equals(categoria_1));
		Assert.assertFalse(categoria_1.equals(categoria_2));
		categoria_1.setNombre("nombre");
		Assert.assertTrue(categoria_2.equals(categoria_1));
		Assert.assertTrue(categoria_1.equals(categoria_2));
		categoria_1.setNombre(null);
		categoria_2.setNombre(null);
		Assert.assertTrue(categoria_2.equals(categoria_1));
		Assert.assertTrue(categoria_1.equals(categoria_2));
		
	}
	
	@Test
	public void test_validarNombre_true() {
		Categoria categoria_2 = new Categoria("nombre", "Descripcion");
		Assert.assertTrue(categoria_2.validarNombre());
	}
	
	@Test
	public void test_validarNombre_false() {
		Categoria categoria_1 = new Categoria(ID_ONE);
		Assert.assertFalse(categoria_1.validarNombre());
	}
	
	@Test
	public void test_hashCode() {
		Categoria categoria_1 = new Categoria();
		Assert.assertEquals(categoria_1.hashCode(), 961);
		categoria_1.setId(ID_ONE);
		Assert.assertEquals(categoria_1.hashCode(), 992);
		categoria_1.setNombre("nombre");
		Assert.assertEquals(categoria_1.hashCode(), -1039903831);
	}
}
