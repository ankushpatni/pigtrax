package com.pigtrax.util;

public class PrimitiveDataUtilAndManipulation {
	public static double getRoundtoNPlace(double data, int decimalPlaces){
		double d = data;
		d = data * Math.pow(10, decimalPlaces);
		d = Math.round(d);
		d = d/Math.pow(10, decimalPlaces);
		return d;
	}

}
