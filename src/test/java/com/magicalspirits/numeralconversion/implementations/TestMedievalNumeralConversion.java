package com.magicalspirits.numeralconversion.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.magicalspirits.numeralconversion.NumeralConverter;
import com.magicalspirits.numeralconversion.implementations.MedievalNumeralModule;

public class TestMedievalNumeralConversion 
{

	private Injector testInjector;
	
	@Before
	public void setup()
	{
		testInjector = Guice.createInjector(new MedievalNumeralModule());
	}
	
	@Test
	public void testOne() 
	{
		//one thousand is interesting because it can be represented by or bars or by M. M is more correct
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("J", nc.convert(1L));
	}
	
	@Test
	public void testThree() 
	{
		//one thousand is interesting because it can be represented by or bars or by M. M is more correct
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("IIJ", nc.convert(3L));
	}
	
	
	@Test
	public void testEnormous() 
	{
		//one thousand is interesting because it can be represented by or bars or by M. M is more correct
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("MMMMBOX", nc.convert(4321));
	}
	
	
	@Test
	public void testLotsOfNumbers()
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		HashSet<String> results = Sets.newHashSetWithExpectedSize(10000);
		for(long l = 1; l < 5000; l ++) 
		{
			String result = nc.convert(l);
			assertTrue("result from " + l + " already added", results.add(result));			
		}
	}	

}
