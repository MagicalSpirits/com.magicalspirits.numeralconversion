package com.magicalspirits.numeralconversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.magicalspirits.numeralconversion.NumeralConverter;
import com.magicalspirits.numeralconversion.USDollarsModule;

public class TestUSCurrencyNumeralConversion 
{
	
	@Test(expected=IllegalArgumentException.class)
	public void testZero() 
	{
		Injector testInjector = Guice.createInjector(new USDollarsModule(false, false));
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		nc.convert(0);
		fail();
	}

	@Test
	public void testPenny() 
	{
		Injector testInjector = Guice.createInjector(new USDollarsModule(false, false));
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("penny", nc.convert(1));
	}
	
	@Test
	public void testSevenCents() 
	{
		Injector testInjector = Guice.createInjector(new USDollarsModule(false, false));
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("nickle, penny, penny", nc.convert(7));
	}
	
	@Test
	public void testUncommonCurrency() 
	{
		Injector testInjector = Guice.createInjector(new USDollarsModule(true, false));
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("two dollar bill", nc.convert(200));
	}
	
	@Test
	public void testLegacyCurrency() 
	{
		Injector testInjector = Guice.createInjector(new USDollarsModule(false, true));
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("ten thousand dollar bill, one thousand dollar bill, five hundred dollar bill", nc.convert(1150000));
	}

	@Test
	public void testLottaCash() 
	{
		Injector testInjector = Guice.createInjector(new USDollarsModule(false, false));
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("one hundred dollar bill, one hundred dollar bill, one hundred dollar bill, "
				+ "one hundred dollar bill, one hundred dollar bill, one hundred dollar bill, "
				+ "one hundred dollar bill, one hundred dollar bill, one hundred dollar bill, "
				+ "one hundred dollar bill, one hundred dollar bill, one hundred dollar bill, "
				+ "one hundred dollar bill, one hundred dollar bill, one hundred dollar bill, "
				+ "one hundred dollar bill, one hundred dollar bill, "
				+ "fifty dollar bill, twenty dollar bill, ten dollar bill, five dollar bill, "
				+ "dollar, dollar, quarter, quarter, quarter, dime, dime, penny, penny, penny, penny", nc.convert(178799));
	}

}
