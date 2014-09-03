package com.magicalspirits.numeralconversion.implementations;

import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.magicalspirits.numeralconversion.NumeralConverter;
import com.magicalspirits.numeralconversion.SortedNumeralConverter;

public class RomanNumeralModule extends AbstractModule {

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
		conversionList.put(1L, "I");
		conversionList.put(5L, "V");
		conversionList.put(10L, "X");
		conversionList.put(50L, "L");
		conversionList.put(100L, "C");
		conversionList.put(500L, "D");
		conversionList.put(1000L, "M");
		//Special Cases
		conversionList.put(4L, "IV");
		conversionList.put(9L, "IX");
		conversionList.put(40L, "XL");
		conversionList.put(90L, "XC");
		conversionList.put(400L, "CD");
		conversionList.put(900L, "CM");
		
		//overline for multiple of 1000
		//note: iterator is fragile, so convert to array
		for(long number : conversionList.keySet().toArray(new Long[0]))
		{
			String value = conversionList.get(number);
			StringBuilder sb = new StringBuilder();
			for(char oneChar : value.toCharArray())
			{
				sb.append(oneChar);
				sb.append("\u0305"); //over-line preceding character
			}
			long newKey = number * 1000L;
			if(!conversionList.containsKey(newKey)) //existing match is more correct
				conversionList.put(newKey, sb.toString());
		}
		
		//OR bars surrounding for multiple of 100000
		//note: iterator is fragile, so convert to array
		for(long number : conversionList.keySet().toArray(new Long[0]))
		{
			String value = conversionList.get(number);
			long newKey = number * 100000L;
			if(!conversionList.containsKey(newKey)) //existing match is more correct
				conversionList.put(newKey,  "|" + value + "|");
		}
		
		//zero
		//About zero, wikipedia says: 
		// The number zero does not have its own Roman numeral, but the word nulla (the Latin word meaning "none") 
		// was used by medieval computists in lieu of 0. Dionysius Exiguus was known to use nulla 
		// alongside Roman numerals in 525.[19][20] About 725, Bede or one of his colleagues used the letter N, 
		// the initial of nulla, in a table of epacts, all written in Roman numerals.
		conversionList.put(0L, "N"); 
		return conversionList;
	}
	
	public static void main(String[] args)
	{
		Injector injector = Guice.createInjector(new RomanNumeralModule());
		NumeralConverter nc = injector.getInstance(NumeralConverter.class);
		for(int i = 0; i < 100001; i++)
		{
			System.out.println(i + " converts to " + nc.convert(i));
		}
	}
}
