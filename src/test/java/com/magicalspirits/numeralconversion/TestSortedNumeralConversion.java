package com.magicalspirits.numeralconversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.SortedMap;
import java.util.TreeMap;

import lombok.RequiredArgsConstructor;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.magicalspirits.numeralconversion.NumeralConverter;
import com.magicalspirits.numeralconversion.SortedNumeralConverter;

public class TestSortedNumeralConversion 
{
	private Injector testInjector;
	
	@Before
	public void setup()
	{
		testInjector = Guice.createInjector(new TestNumeralConversion(true, true));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNegative()
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		nc.convert(-1);
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnsupportedZero()
	{
		Injector i = Guice.createInjector(new TestNumeralConversion(false, true));
		NumeralConverter nc = i.getInstance(NumeralConverter.class);
		nc.convert(0);
		fail();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnsupportedOne()
	{
		Injector i = Guice.createInjector(new TestNumeralConversion(false, false));
		NumeralConverter nc = i.getInstance(NumeralConverter.class);
		nc.convert(13);
		fail();
	}
	
	@Test
	public void testZero() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("Zero", nc.convert(0));
	}

	@Test
	public void testOne() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("One", nc.convert(1));

	}

	@Test
	public void testTen() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("Ten", nc.convert(10));
	}

	@Test
	public void testTenOneOne() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("TenOneOne", nc.convert(12));
	}
	
	@Test
	public void testTenTenOneOne() 
	{
		NumeralConverter nc = testInjector.getInstance(NumeralConverter.class);
		assertEquals("TenTenOneOne", nc.convert(22));
	}
	
	@RequiredArgsConstructor
	private class TestNumeralConversion extends AbstractModule
	{
		private final boolean allowZero;
		private final boolean allowOne;
		
		@Override
		protected void configure() 
		{
			bind(NumeralConverter.class).to(SortedNumeralConverter.class);
		}
		
		@Provides @Named(SortedNumeralConverter.SORTED_CONVERSION_LIST) @Singleton
		public SortedMap<Long, String> getConversionList()
		{
			TreeMap<Long, String> conversionList = Maps.newTreeMap();
			conversionList.put(10L, "Ten");
			if(allowOne)
			{
				conversionList.put(1L, "One");
			}
			if(allowZero)
			{
				conversionList.put(0L, "Zero");
			}

			return conversionList;
		}
	}
}
