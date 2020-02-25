package cuffaro.hernan.hulkStore.web.action;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * 
 * @author hernan.d.cuffaro
 *
 */
@Component
@RequestScope
public class HomeResolverAction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7583769198323876809L;
	
	/**
	 * Redirige a la pagina para crear Categoria.
	 * @return
	 */
	public String redirectCrearCategoria() {
		return  "crear-categoria.xhtml";
	}

}
