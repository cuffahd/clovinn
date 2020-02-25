package cuffaro.hernan.hulkStore.model;

import java.util.Arrays;
/**
 * 
 * Tipos de operaciones disponibles para trabajar sobre el inventario.
 * @author hernan.d.cuffaro
 *
 */
public enum TipoOperacionEnum {

	ADICION("ADICION"), RETIRO("RETIRO");
	
	private String value;

	private TipoOperacionEnum(String value) {
		this.value = value;
	}

	public static TipoOperacionEnum fromValue(String value) {
		for (TipoOperacionEnum tipoOperacion : values()) {
			if (tipoOperacion.value.equalsIgnoreCase(value)) {
				return tipoOperacion;
			}
		}
		throw new IllegalArgumentException(
				"Valor desconocido: " + value + ", Los valores disponibles son: " + Arrays.toString(values()));
	}
}
