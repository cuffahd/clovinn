package cuffaro.hernan.hulkStore.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.service.ProductoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HulkStoreApplication.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:db-test.properties")
public class ProductoRestControllerTest {

	private static final Logger logger = LogManager.getLogger(ProductoRestControllerTest.class);
	
	@InjectMocks
    ProductoRestController productoController;
	
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private ProductoService service;
    
    @Autowired
    ObjectMapper objectmapper;
    
    Producto prod1;
    
    private static final Long ID_ONE = 1L;
    
    @Before
    public void setup() {
    	prod1 = new Producto();
    	prod1.setId(ID_ONE);
    	prod1.setCategoria(new Categoria(ID_ONE));
    	prod1.setDescripcion("description");
    	prod1.setMarca("marca");
    	prod1.setNombre("nombre");
    	prod1.setStock(10L);
    	
    }
    
    @Test
    public void test_getAll() throws Exception {
    	List<Producto> prodList = new ArrayList<Producto>();
    	prodList.add(prod1);
    	
    	Mockito.when(service.getAllProductos()).thenReturn(prodList);
    	String response = mvc.perform(get("/api/productos"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("[{\"id\":1,\"stock\":10,\"descripcion\":\"description\",\"nombre\":\"nombre\","
        		+ "\"marca\":\"marca\",\"categoria\":{\"id\":1,\"nombre\":null,\"descripcion\":null}}]", response);
    }
    
    @Test
    public void test_getProducto() throws Exception, ServiceException {    	
    	Mockito.when(service.getProductoById(Mockito.anyLong())).thenReturn(prod1);
    	String response = mvc.perform(get("/api/productos/{id}",1))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("{\"id\":1,\"stock\":10,\"descripcion\":\"description\",\"nombre\":\"nombre\",\"marca\":"
        		+ "\"marca\",\"categoria\":{\"id\":1,\"nombre\":null,\"descripcion\":null}}", response);
    }
    
    @Test
    public void test_getProducto_exception() throws Exception, ServiceException {
    	Mockito.when(service.getProductoById(Mockito.anyLong())).thenThrow(new ServiceException()) ;
    	String errorMessage = mvc.perform(get("/api/productos/{id}",1))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn().getResolvedException().getLocalizedMessage();
                
                logger.info("error message: " + errorMessage);
                Assert.assertEquals("400 BAD_REQUEST", errorMessage);
    }
    
    @Test
    public void test_saveProducto() throws Exception, ServiceException {
    	Mockito.when(service.saveOrUpdate(Mockito.any())).thenReturn(prod1);
    	String response = mvc.perform(post("/api/productos")
                .content(objectmapper.writeValueAsString(prod1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("Producto guardado de manera correcta, ID: 1", response);
    }
    
    
    @Test
    public void test_saveProducto_serviceException() throws Exception, ServiceException {
    	
    	Mockito.doThrow(new ServiceException("Error forzado")).when(service).saveOrUpdate(Mockito.any());
    	    	
    	String errorMessage = mvc.perform(post("/api/productos")
                .content(objectmapper.writeValueAsString(prod1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn().getResolvedException().getLocalizedMessage();
 
        logger.info("error message: " + errorMessage);
        Assert.assertEquals("400 BAD_REQUEST \"Error forzado\"; nested exception is cuffaro.hernan.hulkStore.excepciones.ServiceException: Error forzado",
        		errorMessage);
    }
    
    @Test
    public void test_deleteProducto() throws Exception, ServiceException {
    	Mockito.doNothing().when(service).delete(Mockito.anyLong());
    	String response = mvc.perform(delete("/api/productos/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("Producto borrado correctamente", response);
    }
    
    @Test
    public void test_deleteProducto_exception() throws Exception, ServiceException {
    	Mockito.doThrow(new ServiceException()).when(service).delete(Mockito.anyLong());
    	String response = mvc.perform(delete("/api/productos/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn().getResolvedException().getLocalizedMessage();
 
        logger.info("response: " + response);
        Assert.assertEquals("400 BAD_REQUEST; nested exception is cuffaro.hernan.hulkStore.excepciones.ServiceException", response);
    }
}
