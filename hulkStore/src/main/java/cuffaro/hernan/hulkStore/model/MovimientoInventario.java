package cuffaro.hernan.hulkStore.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa un movimiento del inventario
 * 
 * @author hernan.d.cuffaro
 *
 */
@Entity
@Table(name="MOVIMIENTO_INVENTARIO")
public class MovimientoInventario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9143482618065501015L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Producto producto;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoOperacionEnum operacion;
	
	@Column(name="movi_cantidad", nullable = false)
	private Integer cantidad;
	
	@Temporal(TemporalType.DATE)
	@Column(name="MOV_FECHA")
	private Date fecha;

	public MovimientoInventario() {
		this.fecha = new Date();
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public TipoOperacionEnum getOperacion() {
		return operacion;
	}

	public void setOperacion(TipoOperacionEnum operacion) {
		this.operacion = operacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public boolean validarCamposMinimos() {
		boolean isValid = true;
		if(this.producto==null || this.producto.getId()==null) {
			isValid = false;
		} else if(this.operacion==null) {
			isValid = false;
		} else if(this.cantidad==null || this.cantidad<=0) {
			isValid = false;
		}
		
		return isValid;
		
	}
	
}
