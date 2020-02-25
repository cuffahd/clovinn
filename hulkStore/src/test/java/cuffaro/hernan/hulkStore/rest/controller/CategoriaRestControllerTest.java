package cuffaro.hernan.hulkStore.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cuffaro.hernan.hulkStore.HulkStoreApplication;
import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Categoria;
import cuffaro.hernan.hulkStore.service.CategoriaService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HulkStoreApplication.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:db-test.properties")
public class CategoriaRestControllerTest {

	private static final Logger logger = LogManager.getLogger(CategoriaRestControllerTest.class);
	
	@InjectMocks
    CategoriaRestController cateController;
	
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private CategoriaService service;
    
    @Autowired
    ObjectMapper objectmapper;
    
    Categoria cate1;
    
    @Before
    public void setup() {
    	cate1 =  new Categoria();
    	cate1.setId(1L);
    	cate1.setDescripcion("Descripcion de categoria");
    	cate1.setNombre("Nombre de categoria");
    }
    
    @Test
    public void test_getAll() throws Exception {
    	Set<Categoria> cateSet = new HashSet<Categoria>();
    	cateSet.add(cate1);
    	
    	Mockito.when(service.getAllCategorias()).thenReturn(cateSet);
    	String response = mvc.perform(get("/api/categorias"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("[{\"id\":1,\"nombre\":\"Nombre de categoria\",\"descripcion\":\"Descripcion de categoria\"}]", response);
    }
    
    @Test
    public void test_getCategoria() throws Exception, ServiceException {    	
    	Mockito.when(service.getCategoriaById(Mockito.anyLong())).thenReturn(cate1);
    	String response = mvc.perform(get("/api/categorias/{id}",1))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("{\"id\":1,\"nombre\":\"Nombre de categoria\",\"descripcion\":\"Descripcion de categoria\"}", response);
    }
    
    @Test
    public void test_getCategoria_exception() throws Exception, ServiceException {
    	Mockito.when(service.getCategoriaById(Mockito.anyLong())).thenThrow(new ServiceException()) ;
    	String errorMessage = mvc.perform(get("/api/categorias/{id}",1))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn().getResolvedException().getLocalizedMessage();
                
                logger.info("error message: " + errorMessage);
                Assert.assertEquals("400 BAD_REQUEST", errorMessage);
    }
    
    @Test
    public void test_saveCategoria() throws Exception, ServiceException {
    	Mockito.doNothing().when(service).saveOrUpdate(Mockito.any());
    	String response = mvc.perform(post("/api/categorias")
                .content(objectmapper.writeValueAsString(cate1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("Categoria guardada correctamente, ID: 1", response);
    }
    
    
    @Test
    public void test_saveCategoria_categoriaSinNombre() throws Exception, ServiceException {
    	Categoria cate1 =  new Categoria();
    	cate1.setDescripcion("Descripcion de categoria");
    	
    	Mockito.doNothing().when(service).saveOrUpdate(Mockito.any());
    	    	
    	String errorMessage = mvc.perform(post("/api/categorias")
                .content(objectmapper.writeValueAsString(cate1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn().getResolvedException().getLocalizedMessage();
 
        logger.info("error message: " + errorMessage);
        Assert.assertEquals("400 BAD_REQUEST \"Nombre no puede ser nulo o vacio\"", errorMessage);
    }
    
    @Test
    public void test_saveCategoria_serviceException() throws Exception, ServiceException {
    	Categoria cate1 =  new Categoria();
    	cate1.setDescripcion("Descripcion de categoria");
    	cate1.setNombre("nombre");
    	
    	Mockito.doThrow(new ServiceException("Error forzado")).when(service).saveOrUpdate(Mockito.any());
    	    	
    	String errorMessage = mvc.perform(post("/api/categorias")
                .content(objectmapper.writeValueAsString(cate1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CONFLICT.value()))
                .andReturn().getResolvedException().getLocalizedMessage();
 
        logger.info("error message: " + errorMessage);
        Assert.assertEquals("409 CONFLICT \"Error forzado\"; nested exception is cuffaro.hernan.hulkStore.excepciones.ServiceException: Error forzado", errorMessage);
    }
    
    @Test
    public void test_deleteCategoria() throws Exception, ServiceException {
    	Mockito.doNothing().when(service).delete(Mockito.anyLong());
    	String response = mvc.perform(delete("/api/categorias/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("Categoria borrada correctamente", response);
    }
    
    @Test
    public void test_deleteCategoria_exception() throws Exception, ServiceException {
    	Mockito.doThrow(new ServiceException()).when(service).delete(Mockito.anyLong());
    	String response = mvc.perform(delete("/api/categorias/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn().getResolvedException().getLocalizedMessage();
 
        logger.info("response: " + response);
        Assert.assertEquals("400 BAD_REQUEST; nested exception is cuffaro.hernan.hulkStore.excepciones.ServiceException", response);
    }
  
}
