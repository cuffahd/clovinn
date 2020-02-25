package cuffaro.hernan.hulkStore.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Categoria;
import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.repository.IProductoDao;
import cuffaro.hernan.hulkStore.utils.IteratorUtilsTest;

public class ProductoServiceTest {
	
	@Mock
    IProductoDao productoDao;
    
    @Mock
    ICategoriaService categoriaService;
	
	@InjectMocks
	ProductoService productoService;
		
	Producto producto_1;
	
	Producto producto_2;
	
	private static final Long ID_ONE = 1L;
	private static final Long ID_TWO = 2L;
	
	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	    producto_1 = new Producto();
	    producto_1.setId(ID_ONE);
	    producto_2 = new Producto();
	    producto_2.setId(ID_TWO);
	}
	    
	    
	@Test
	public void test_getProductoById() throws ServiceException {
		Mockito.when(productoDao.findById(Mockito.anyLong())).thenReturn(Optional.of(producto_2)); 
	    assertEquals(productoService.getProductoById(ID_ONE), producto_2);
	}
	
	@Test(expected = ServiceException.class)
	public void test_getProductoById_exception() throws ServiceException {
		Mockito.when(productoDao.findById(Mockito.anyLong())).thenThrow(new NoSuchElementException()) ; 
	    productoService.getProductoById(ID_ONE);
	    Assert.fail();
	}

	@Test (expected = ServiceException.class)
	public void test_validarCampos_camposMinimosInvalidos() throws ServiceException{
		productoService.validarCampos(producto_1);
		Assert.fail();
	}
	
	@Test (expected = ServiceException.class)
	public void test_validarCampos_categoriaInexistente() throws ServiceException {
		Producto p1 = new Producto();
		p1.setNombre("nombre");
		Categoria cate1 = new Categoria();
		cate1.setId(ID_ONE);
		p1.setCategoria(cate1);
		
		Mockito.when(categoriaService.existeCategoria(Mockito.any())).thenReturn(Boolean.FALSE);
		productoService.validarCampos(p1);
		Assert.fail();
	}
	
	@Test 
	public void test_validarCampos_return_true() throws ServiceException {
		Producto p1 = new Producto();
		p1.setNombre("nombre");
		Categoria cate1 = new Categoria();
		cate1.setId(ID_ONE);
		p1.setCategoria(cate1);
		
		Mockito.when(categoriaService.existeCategoria(Mockito.any())).thenReturn(Boolean.TRUE);
		Assert.assertTrue(productoService.validarCampos(p1));
	}
	
	@Test
	public void test_existeProducto_true() {
		Mockito.when(productoDao.findById(Mockito.anyLong())).thenReturn(Optional.of(producto_2)); 
		Assert.assertTrue(productoService.existeProducto(producto_2));
	}
	
	@Test
	public void test_existeProducto_false() {
		Mockito.when(productoDao.findById(Mockito.anyLong())).thenReturn(Optional.of(producto_2)); 
		Assert.assertFalse(productoService.existeProducto(producto_1));
	}
	
	@Test
	public void test_existeProducto_false_exception() {
		Mockito.when(productoDao.findById(Mockito.anyLong())).thenThrow(new NoSuchElementException()) ;  
		Assert.assertFalse(productoService.existeProducto(producto_1));
	}
	
	
	@Test
	public void test_getAll() {
		List<Producto> prodList = new ArrayList<Producto>();
		prodList.add(producto_1);
		Iterable<Producto> prodIterator =  IteratorUtilsTest.iteratorToIterable(prodList.iterator());
		
		Mockito.when(productoDao.findAll()).thenReturn(prodIterator);
		
		List<Producto> returnedList = productoService.getAllProductos();
		Assert.assertEquals(1, returnedList.size());
		Assert.assertEquals(prodList, returnedList);
	}	
	
	@Test
	public void test_deleteProductByCategoria_categoriaInexistente() {
		Mockito.doThrow(new EmptyResultDataAccessException(1)).when(productoDao).deleteByCategoria(Mockito.any());
		productoService.deleteProductByCategoria(ID_ONE);
	}
	
	@Test (expected = ServiceException.class)
	public void test_save_integrityViolation() throws ServiceException {
		Mockito.when(productoDao.save(Mockito.any())).thenThrow(new DataIntegrityViolationException("ya existe producto"));
		productoService.saveOrUpdate(producto_1);
		Assert.fail();
	}
	
}
