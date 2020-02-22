package cuffaro.hernan.hulkStore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="categoria")
@Table(name="CATEGORIA")
public class Categoria implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9196134354897075272L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cate_id")
	private Long id;
	
	@Column(name="cate_nombre", nullable=false, unique=true)
	private String nombre;
	
	@Column(name="cate_desc")
	private String descripcion;

	@OneToMany(
	        mappedBy = "categoria",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<Producto> productos=new ArrayList<Producto>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
}
