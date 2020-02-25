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
import cuffaro.hernan.hulkStore.model.Categoria;
import cuffaro.hernan.hulkStore.service.ICategoriaService;

/**
 * 
 * Controlador donde se exponen los endpoints para trabajar con Categorias.
 * 
 * @author hernan.d.cuffaro
 *
 */
@RestController
@RequestMapping("api")
public class CategoriaRestController {

    @Autowired
    ICategoriaService categoriaService;
    
    private static final Logger logger = LogManager.getLogger(CategoriaRestController.class);
    
    /**
     * Devuelve todas las categorias disponibles.
     * 
     * @return
     */
    @GetMapping("/categorias")
    private ResponseEntity<Object> getAllCategorias() {
    	logger.debug("Buscando todas las categorias ");
        return new ResponseEntity<Object>(categoriaService.getAllCategorias(), HttpStatus.OK);
    }

    /**
     * Devuelve la categoria Indicada por el ID.
     * @param id
     * @return
     */
    @GetMapping("/categorias/{id}")
    private Categoria getCategoria(@PathVariable("id") long id) {
    	logger.debug("Buscando categoria: " + id);
    	try {
			return categoriaService.getCategoriaById(id);
		} catch (ServiceException e) {
			 throw new ResponseStatusException(
     		         HttpStatus.BAD_REQUEST, e.getMessage());
		}
    }

    /**
     * Borra una categoria segun el ID
     * @param id
     * @return
     */
    @DeleteMapping("/categorias/{id}")
    private  ResponseEntity<Object> deleteCategoria(@PathVariable("id") long id) {
        try {
        	logger.info("Procesando request de DELETE id:" + id);
      	  	categoriaService.delete(id);
      	  	logger.info("Procesado de manera correcta");
      	  	return new ResponseEntity<Object>("Categoria borrada correctamente", HttpStatus.OK);
        } catch (ServiceException e) {
        	 throw new ResponseStatusException(
          	         HttpStatus.BAD_REQUEST, e.getMessage(),e);
        
        }  catch (Exception exc) {
  	       throw new ResponseStatusException(
  	         HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error procesando la solicitud.", exc);
        }
    }

    /**
     * Persiste una categoria si la misma es valida.
     * @param categoria
     * @return
     */
    @PostMapping("/categorias")
    private Long saveCategoria(@RequestBody Categoria categoria) {
      try {
    	  logger.info("Procesando request");
    	  if(!categoria.validarNombre()) {
    		  throw new ResponseStatusException(
     		         HttpStatus.BAD_REQUEST, "Nombre no puede ser nulo o vacio");
    	  }
    	  categoriaService.saveOrUpdate(categoria);
    	  logger.info("Procesado de manera correcta");
    	  return categoria.getId();
      } catch (ServiceException e) {
    	  throw new ResponseStatusException(
    		         HttpStatus.CONFLICT, e.getMessage(), e);
    	 
      } catch (Exception exc) {
	       throw new ResponseStatusException(
	         HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error procesando la solicitud.", exc);
      }
    }

}