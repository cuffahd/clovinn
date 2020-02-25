package cuffaro.hernan.hulkStore.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Categoria;
import cuffaro.hernan.hulkStore.repository.ICategoriaDao;
import cuffaro.hernan.hulkStore.utils.IteratorUtilsTest;

public class CategoriaServiceTest{
	
	@Mock
    ICategoriaDao categoriaDao;
	    
	@Mock
	IProductoService productoService;
	
	@InjectMocks
	CategoriaService categoriaService;
	
	Categoria categoria_1;
	
	Categoria categoria_2;
	
	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	    categoria_1 = new Categoria();
	    categoria_1.setId(1L);
	    categoria_2 = new Categoria();
	    categoria_2.setId(2L);
	}
	
	@Test
	public void test_existeCategoria() throws ServiceException {
		Mockito.when(categoriaDao.findById(Mockito.anyLong())).thenReturn(Optional.of(categoria_1)); 
	    assertEquals(categoriaService.existeCategoria(categoria_1), true);
	}
	
	@Test
	public void test_existeCategoria_false() throws ServiceException {
		Mockito.when(categoriaDao.findById(Mockito.anyLong())).thenReturn(Optional.of(categoria_1)); 
	    assertEquals(categoriaService.existeCategoria(categoria_2), false);
	}
	
	@Test
	public void test_existeCategoria_exception() throws ServiceException {
		Mockito.when(categoriaDao.findById(Mockito.anyLong())).thenThrow(new NoSuchElementException()); 
	    assertEquals(categoriaService.existeCategoria(categoria_2), false);
	}
	
	@Test(expected = ServiceException.class)
	public void test_saveOrUpdate_throwException() throws ServiceException {
		Mockito.when(categoriaDao.save(categoria_1)).thenThrow(new DataIntegrityViolationException("error"));
	    categoriaService.saveOrUpdate(categoria_1);
	    Assert.fail();
	}
	
	@Test
	public void test_getAll() {
		List<Categoria> cateList = new ArrayList<Categoria>();
		cateList.add(categoria_1);
		Set<Categoria> cateSet = new HashSet<Categoria>();
		cateSet.add(categoria_1);
		Iterable<Categoria> cateIterator =   IteratorUtilsTest.iteratorToIterable(cateList.iterator());
		
		Mockito.when(categoriaDao.findAll()).thenReturn(cateIterator);
		
		Set<Categoria> returnedSet = categoriaService.getAllCategorias();
		Assert.assertEquals(1, returnedSet.size());
		Assert.assertEquals(cateSet, returnedSet);
	}
	
}