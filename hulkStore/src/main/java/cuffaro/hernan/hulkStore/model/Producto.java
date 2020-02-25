package cuffaro.hernan.hulkStore.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

/**
 * Entidad que representa un Producto
 * 
 * @author hernan.d.cuffaro
 *
 */
@Entity(name="producto")
@Table(name = "PRODUCTO")
public class Producto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5249026996987695247L;
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="PROD_STOCK")
	private  Long stock;
	
	@Column(name="PROD_DESC")
	private String descripcion;
	
	@Column(name="PROD_NOMBRE", nullable = false)
	private String nombre;
	
	@Column(name = "PROD_MARCA")
	private String marca;
	
	@OneToOne
	private Categoria categoria;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	/**
	 * Valida campos minimos necesarios para que un producto sea valido.
	 * @return true si el producto tiene los campos minimos.
	 */
	public boolean validarCamposMinimos() {
		boolean isValid = true;
		if(StringUtils.isBlank(this.nombre)) {
			isValid = false;
		}else if(this.categoria==null || this.categoria.getId()==null) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Metodo que modifica el stock de acuerdo a la operacion que se realiza.
	 * @param cantidad
	 * @param operacion
	 */
	public void modificarStock(Long cantidad, TipoOperacionEnum operacion) {
		if(TipoOperacionEnum.ADICION.equals(operacion)) {
			this.stock = stock + cantidad;
		}else {
			this.stock = stock- cantidad;
		}
		
	}
	

}
