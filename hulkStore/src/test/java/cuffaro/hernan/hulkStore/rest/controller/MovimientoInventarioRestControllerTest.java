package cuffaro.hernan.hulkStore.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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
import cuffaro.hernan.hulkStore.model.MovimientoInventario;
import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.model.TipoOperacionEnum;
import cuffaro.hernan.hulkStore.service.IMovimientoInventarioService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HulkStoreApplication.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:db-test.properties")
public class MovimientoInventarioRestControllerTest {

private static final Logger logger = LogManager.getLogger(CategoriaRestControllerTest.class);
	
	@InjectMocks
    MovimientoInventarioRestController miController;
	
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    IMovimientoInventarioService movimientoInventarioService;
    
    @Autowired
    ObjectMapper objectmapper;
    
    MovimientoInventario mi;
    
    @Before
    public void setup() {
    	Date fecha = new Date(1111111111);
    	mi = new MovimientoInventario();
    	mi.setCantidad(10);
    	mi.setFecha(fecha);
    	mi.setId(1L);
    	mi.setOperacion(TipoOperacionEnum.ADICION);
    	mi.setProducto(new Producto());
    }
    
    @Test
    public void test_getAll() throws Exception {
    	
    	List<MovimientoInventario> miList = new ArrayList<MovimientoInventario>();
    	miList.add(mi);
    	Mockito.when(movimientoInventarioService.getAllMovimientosInventario()).thenReturn(miList);
    	String response = mvc.perform(get("/api/movimientoInventario"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn().getResponse().getContentAsString();
 
        logger.info("response: " + response);
        Assert.assertEquals("[{\"id\":1,\"producto\":{\"id\":null,\"stock\":null,\"descripcion\":null,\"nombre\":null,\"marca\":null,\"categoria\":null},\"operacion\":\"ADICION\",\"cantidad\":10,\"fecha\":\"1970-01-13T20:38:31.111+0000\"}]", response);
    }
    
    @Test
    public void test_saveMovimientoInventario() throws ServiceException, UnsupportedEncodingException, Exception {
    	
    	Mockito.doNothing().when(movimientoInventarioService).validarOperacion(Mockito.any());
    	Mockito.doNothing().when(movimientoInventarioService).saveOrUpdate(Mockito.any());
    	
    	String response = mvc.perform(post("/api/movimientoInventario")
                .content(objectmapper.writeValueAsString(mi))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();
	
		logger.info("response: " + response);
	    Assert.assertEquals("Movimiento guardado de manera correcta", response);
     
     
    }
	
    @Test
    public void test_saveMovimientoInventario_serviceException() throws ServiceException, UnsupportedEncodingException, Exception {
    	
    	Mockito.doNothing().when(movimientoInventarioService).validarOperacion(Mockito.any());
    	Mockito.doThrow(new ServiceException()).when(movimientoInventarioService).saveOrUpdate(Mockito.any());
    	
    	String errorMessage = mvc.perform(post("/api/movimientoInventario")
                .content(objectmapper.writeValueAsString(mi))
                .contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
    	        .andReturn().getResolvedException().getLocalizedMessage();
	
    	logger.info("error message: " + errorMessage);
    	Assert.assertEquals("400 BAD_REQUEST; nested exception is cuffaro.hernan.hulkStore.excepciones.ServiceException", errorMessage);
     
     
    }
}