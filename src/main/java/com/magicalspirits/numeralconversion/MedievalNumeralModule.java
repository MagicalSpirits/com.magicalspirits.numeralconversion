package com.magicalspirits.numeralconversion;

import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

public class MedievalNumeralModule extends AbstractModule 
{

	@Override
	protected void configure() 
	{
		bind(NumeralConverter.class).to(SortedNumeralConverter.class);
	}
	
	@Provides @Named(SortedNumeralConverter.SORTED_CONVERSION_LIST) @Singleton
	public SortedMap<Long, String> getConversionList()
	{
		TreeMap<Long, String> conversionList = Maps.newTreeMap();
		//Standard numbers
		conversionList.put(1L, "J");
		conversionList.put(2L, "IJ");
		conversionList.put(3L, "IIJ");
		conversionList.put(5L, "A");
		conversionList.put(6L, "Ϛ");
		conversionList.put(7L, "Z");
		conversionList.put(10L, "X");
		conversionList.put(11L, "O");
		conversionList.put(40L, "F");
		conversionList.put(50L, "L");
		conversionList.put(70L, "S");
		conversionList.put(80L, "R");
		conversionList.put(90L, "N");
		conversionList.put(100L, "C");
		conversionList.put(150L, "Y");
		conversionList.put(151L, "K");
		conversionList.put(160L, "T");
		conversionList.put(200L, "H");
		conversionList.put(250L, "E");
		conversionList.put(300L, "B");
		conversionList.put(400L, "P");
		conversionList.put(500L, "Q");
		conversionList.put(1000L, "M");
		//Special Cases
		conversionList.put(4L, "IV");
		conversionList.put(9L, "IX");
		conversionList.put(900L, "CM");
		return conversionList;
	}
	
	public static void main(String[] args)
	{
		Injector injector = Guice.createInjector(new MedievalNumeralModule());
		NumeralConverter nc = injector.getInstance(NumeralConverter.class);
		for(int i = 1; i < 4000; i++)
		{
			System.out.println(i + " converts to " + nc.convert(i));
		}
	}
}

