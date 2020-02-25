package cuffaro.hernan.hulkStore.rest.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.MovimientoInventario;
import cuffaro.hernan.hulkStore.service.IMovimientoInventarioService;

/**
 * 
 * Endpoint para trabajar sobre los movimientos de inventarios
 * 
 * @author hernan.d.cuffaro
 *
 */
@RestController
@RequestMapping("api")
public class MovimientoInventarioRestController {

    @Autowired
    IMovimientoInventarioService movimientoInventarioService;

    private static final Logger logger = LogManager.getLogger(MovimientoInventarioRestController.class);
    
    /**
     * Lista todos los movimientos realizados hasta el momento.
     * @return
     */
    @GetMapping("/movimientoInventario")
    private  ResponseEntity<Object> getAllMovimientoInventario() {
        logger.debug("Buscando todos los movimientos");
        return new ResponseEntity<Object>(movimientoInventarioService.getAllMovimientosInventario(), HttpStatus.OK);
    }

    /**
     * Persiste el movimiento del inventario.
     * 
     * @param movimientoInventario
     * @return
     */
    @PostMapping("/movimientoInventario")
    private ResponseEntity<Object> saveMovimientoInventario(@RequestBody MovimientoInventario movimientoInventario) {
        try {
        	  logger.info("Procesando request");
        	  movimientoInventarioService.validarOperacion(movimientoInventario);
        	  movimientoInventarioService.saveOrUpdate(movimientoInventario);
        	  logger.info("Procesado de manera correcta");
        	  return new ResponseEntity<Object>("Movimiento guardado de manera correcta", HttpStatus.CREATED);
          } catch (ServiceException e) {
        	  throw new ResponseStatusException(
        		         HttpStatus.BAD_REQUEST, e.getMessage(), e);
          } catch (Exception exc) {
    	       throw new ResponseStatusException(
    	         HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error procesando la solicitud.", exc);
          }
    }
}