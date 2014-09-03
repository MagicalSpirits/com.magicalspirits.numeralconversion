package com.magicalspirits.numeralconversion;

public interface NumeralConverter {

	/**
	 * 
	 * @param value
	 * @return
	 * @throws IllegalArgumentException 
	 * 		If the value cannot be converted.
	 */
	public abstract String convert(long value) throws IllegalArgumentException;

}