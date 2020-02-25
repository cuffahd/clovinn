package cuffaro.hernan.hulkStore.rest.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Producto;
import cuffaro.hernan.hulkStore.service.IProductoService;

/**
 * 
 * @author hernan.d.cuffaro
 *
 */
@RestController
@RequestMapping("api")
public class ProductoRestController {

    @Autowired
    IProductoService productoService;

    private static final Logger logger = LogManager.getLogger(ProductoRestController.class);
    
    @GetMapping("/productos")
    private ResponseEntity<Object> getAllProductos() {
    	logger.debug("Buscando todos los productos");
        return new ResponseEntity<Object>(productoService.getAllProductos(), HttpStatus.OK);
    }

    @GetMapping("/productos/{id}")
    private ResponseEntity<Object> getProducto(@PathVariable("id") long id) {
        logger.debug("Buscando producto: " + id);
    	try {
    		return new ResponseEntity<Object>(productoService.getProductoById(id), HttpStatus.OK);
		} catch (ServiceException e) {
			 throw new ResponseStatusException(
     		         HttpStatus.BAD_REQUEST, e.getMessage());
		}
    }

    @DeleteMapping("/productos/{id}")
    private ResponseEntity<Object> deleteProducto(@PathVariable("id") long id) {
        try {
        	logger.info("Procesando request de DELETE id:" + id);
        	productoService.delete(id);
      	  	logger.info("Borrado correctamente id: " + id);
      	  	return new ResponseEntity<Object>("Producto borrado correctamente", HttpStatus.OK);
        } catch (ServiceException e) {
        	 throw new ResponseStatusException(
          	         HttpStatus.BAD_REQUEST, e.getMessage(),e);
        
        }  catch (Exception exc) {
  	       throw new ResponseStatusException(
  	         HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error procesando la solicitud.", exc);
        }
    }

    @PostMapping("/productos")
    private ResponseEntity<Object> saveProducto(@RequestBody Producto producto) {
        try {
      	  logger.info("Procesando request");
      	  productoService.validarCampos(producto);
      	  productoService.saveOrUpdate(producto);
      	  logger.info("Producto guardado de manera correcta, ID: " + producto.getId() );
      	  return new ResponseEntity<Object>("Producto guardado de manera correcta, ID: " + producto.getId(), HttpStatus.CREATED);
        } catch (ServiceException e) {
      	  throw new ResponseStatusException(
      		         HttpStatus.BAD_REQUEST, e.getMessage(), e);
      	 
        } catch (Exception exc) {
  	       throw new ResponseStatusException(
  	         HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error procesando la solicitud.", exc);
        }
    }
}