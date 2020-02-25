package cuffaro.hernan.hulkStore.web.action;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import cuffaro.hernan.hulkStore.excepciones.ServiceException;
import cuffaro.hernan.hulkStore.model.Categoria;
import cuffaro.hernan.hulkStore.service.CategoriaService;
import cuffaro.hernan.hulkStore.web.form.CategoriaResolverForm;

/**
 * 
 * @author hernan.d.cuffaro
 *
 */
@Component
@RequestScope
public class CategoriaResolverAction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8034560550231379285L;

	private boolean show;
	 private final CategoriaResolverForm categoriaResolverForm;
	 private final CategoriaService categoriaService;
	 
	 public CategoriaResolverAction(@Autowired CategoriaResolverForm categoriaResolverForm,
	                                 @Autowired CategoriaService categoriaService)
	 {
	    this.categoriaService = categoriaService;
		this.categoriaResolverForm = categoriaResolverForm;
	 }
	 
	 /**
	  * Extrae los valores para crear una categoria desde el Form
	  * Los evalua para saber si son validos.
	  * Guarda la nueva categoria en la BD
	  */
	 public void saveCategoria() {
		  Categoria categoria = new Categoria(categoriaResolverForm.getNombre(),categoriaResolverForm.getDescripcion());
		  if(!categoria.validarNombre()) {
			  FacesContext.getCurrentInstance().addMessage("categoriaResolverForm:nombre", new FacesMessage("Nombre Invalido."));
		  }else {
			  // campos validos
			  try {
				 
				  categoriaService.saveOrUpdate(categoria);
				  this.show=true;
				  categoriaResolverForm.setDescripcion("");
				  categoriaResolverForm.setNombre("");
			  } catch (ServiceException e) {
				  FacesContext.getCurrentInstance().addMessage("categoriaResolverForm:nombre", new FacesMessage("Nombre ya existente."));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("categoriaResolverForm:nombre", new FacesMessage("Nombre ya existente."));
			}
		  }
	 }
	 
	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
	
}
