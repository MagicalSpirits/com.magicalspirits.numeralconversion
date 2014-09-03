package com.magicalspirits.numeralconversion.implementations;

import java.util.SortedMap;
import java.util.TreeMap;

import lombok.RequiredArgsConstructor;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.magicalspirits.numeralconversion.NumeralConverter;
import com.magicalspirits.numeralconversion.SortedNumeralConverter;

@RequiredArgsConstructor
public class USDollarsModule extends AbstractModule
{
	private final boolean uncommonCurrencies;
	private final boolean legacyCurrencies;

	@Override
	protected void configure() 
	{
		bind(NumeralConverter.class).to(TrimmingSortedNumeralConverter.class);
	}
	
	@Provides @Named(SortedNumeralConverter.SORTED_CONVERSION_LIST) @Singleton
	public SortedMap<Long, String> getConversionList()
	{
		TreeMap<Long, String> conversionList = Maps.newTreeMap();
		//Standard numbers
		conversionList.put(1L, "penny, ");
		conversionList.put(5L, "nickle, ");
		conversionList.put(10L, "dime, ");
		conversionList.put(25L, "quarter, ");
		if(uncommonCurrencies)
			conversionList.put(50L, "fifty cent piece, ");
		conversionList.put(100L, "dollar, "); //could be coin or bill
		if(uncommonCurrencies)
			conversionList.put(200L, "two dollar bill, ");
		conversionList.put(500L, "five dollar bill, ");
		conversionList.put(1000L, "ten dollar bill, ");
		conversionList.put(2000L, "twenty dollar bill, ");
		conversionList.put(5000L, "fifty dollar bill, ");
		conversionList.put(10000L, "one hundred dollar bill, ");
		if(legacyCurrencies)
		{
			conversionList.put(50000L, "five hundred dollar bill, ");
			conversionList.put(100000L, "one thousand dollar bill, ");
			conversionList.put(1000000L, "ten thousand dollar bill, ");
		}

		return conversionList;
	}
	
	private static class TrimmingSortedNumeralConverter extends SortedNumeralConverter
	{
		@Override
		public String convert(long value) throws IllegalArgumentException
		{
			String rv = super.convert(value);
			if(rv.endsWith(", "))
				rv = rv.substring(0, rv.length() - 2);
			return rv;
		}
	}
	
	public static void main(String[] args)
	{
		Injector injector = Guice.createInjector(new USDollarsModule(false, false));
		NumeralConverter nc = injector.getInstance(NumeralConverter.class);
		for(int i = 1; i < 10000L; i++)
		{
			System.out.println(i + " converts to " + nc.convert(i));
		}
	}
}
