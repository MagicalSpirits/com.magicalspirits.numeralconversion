package com.magicalspirits.numeralconversion;

import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class SortedNumeralConverter implements NumeralConverter 
{
	public static final String SORTED_CONVERSION_LIST = "sorted-conversion-list"; 
	
	//note: using a sorted map forces the elements to be in a specific order. This is important for the conversion below.
	@Inject @Named(SORTED_CONVERSION_LIST) 
	private SortedMap<Long, String> conversionList;
	
	@Override
	public String convert(long value) throws IllegalArgumentException
	{
		if(value == 0)
		{
			if(conversionList.firstKey() != 0)
				throw new IllegalArgumentException("Unable to handle zero in this conversion");
			else
				return conversionList.get(conversionList.firstKey());
		}
		
		if(value < 0)
			throw new IllegalArgumentException("Only positive numbers can be converted");
				
		List<Entry<Long, String>> sortedList = Lists.newArrayList(conversionList.entrySet());
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = sortedList.size() -1; i > -1; i--)
		{
			Entry<Long, String> entry = sortedList.get(i);
			if(entry.getKey() == 0)
				continue; // Zero is handled in the special case at the beginning of this function.
			
			long numberOfElements = value / entry.getKey();
			if(numberOfElements > 0)
			{
				for(long x = 0; x < numberOfElements; x++)
					sb.append(entry.getValue());
			}
			
			//decrement the passed list by the number of times we've removed a valid element from it.
			value -= (entry.getKey() * numberOfElements);
		}
		if(value != 0)
			throw new IllegalArgumentException("The number " + value + " contains digits that cannot be converted.");
		
		if(sb.length() == 0)
			throw new IllegalArgumentException("The number " + value + " cannot be converted");
		return sb.toString();
	}
}
