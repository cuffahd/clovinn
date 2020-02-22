package cuffaro.hernan.hulkStore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class Producto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5249026996987695247L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="PROD_STOCK")
	private  Integer stock;
	
	@Column(name="PROD_DESC")
	private String descripcion;
	
	@Column(name="PROD_NOMBRE")
	private String nombre;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Categoria categoria;
	
	@OneToMany(
	        mappedBy = "",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	    )
	private List<MovimientoInventario> productos=new ArrayList<MovimientoInventario>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
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
	

}
