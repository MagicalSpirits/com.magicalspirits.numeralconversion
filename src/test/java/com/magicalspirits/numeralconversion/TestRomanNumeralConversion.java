package com.magicalspirits.numeralconversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.magicalspirits.numeralconversion.NumeralConverter;
import com.magicalspirits.numeralconversion.RomanNumeralModule;

public class TestRomanNumeralConversion {

	private Injector testInjector;
	
	@Before
	public void setup()
	{
		testInjector = Guice.createInjector(new RomanNumeralModule());
	}

	@Test
	public void testZero() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("N", nc.convert(0));
	}

	@Test
	public void testOne() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("I", nc.convert(1));
	}
	
	@Test
	public void testThree() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("III", nc.convert(3));
	}
	
	@Test
	public void testFour() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("IV", nc.convert(4));
	}
	
	@Test
	public void testNine() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("IX", nc.convert(9));
	}
	
	@Test
	public void testTen() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("X", nc.convert(10));
	}
	
	@Test
	public void testOneThousand() 
	{
		//one thousand is interesting because it can be represented by or bars or by M. M is more correct
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("M", nc.convert(1000));
	}
	
	@Test
	public void testTwoThousand() 
	{
		//one thousand is interesting because it can be represented by or bars or by M. M is more correct
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("MM", nc.convert(2000));
	}
	
	@Test
	public void testTwentyThousand() 
	{
		//one thousand is interesting because it can be represented by or bars or by M. M is more correct
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("X\u0305X\u0305", nc.convert(20000));
	}
	
	@Test
	public void testEnormous() 
	{
		//one thousand is interesting because it can be represented by or bars or by M. M is more correct
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("|M\u0305||M\u0305|", nc.convert(200000000000L));
	}
	
	
	@Test
	public void testLotsOfNumbers()
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		HashSet<String> results = Sets.newHashSetWithExpectedSize(10000);
		for(long l = 0; l < 8888888; l ++) // by 888,888 we've tested almost all the prescribed use cases from the conversion map
		{
			if(l % 40000L == 0  )
				results.clear(); //avoid the out of memory error, and it's pretty safe to assume every 10k will be different
			String result = nc.convert(l);
			assertTrue(results.add(result));			
		}
	}	
}
