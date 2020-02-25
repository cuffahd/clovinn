package cuffaro.hernan.hulkStore.model;

import org.junit.Assert;
import org.junit.Test;

public class TipoOperacionEnumTest {

	@Test(expected = IllegalArgumentException.class)
	public void test_fromValue_excepcion() {
		TipoOperacionEnum.fromValue("ilegal");
		Assert.fail();
	}

	@Test
	public void test_fromValue() {
		Assert.assertEquals(TipoOperacionEnum.RETIRO, TipoOperacionEnum.fromValue("Retiro"));
		Assert.assertEquals(TipoOperacionEnum.ADICION, TipoOperacionEnum.fromValue("ADICION"));
		
	}
}
