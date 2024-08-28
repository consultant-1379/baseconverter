package com.ericsson.maven.plugins.baseconverter;

/**
 */
public class BaseConverter
{
    public static final String BASE2 = "01";
    public static final String BASE10 = "0123456789";
    public static final String BASE16 = "0123456789ABCDEF";
    public static final String BASE36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static int base2int( String value, String baseFrom ) 
    {
    	int x = 0;
    	for ( int i=0; i<value.length(); i++ ) {
    		x = x * baseFrom.length() + baseFrom.indexOf( value.charAt(i) );
    	}
    	return x;
    }

    public static String int2base( int x, String baseTo )
    {
	String res = "";
	if ( x == 0 ) {
		res = Character.toString(baseTo.charAt(0));
	} else {
		while (x > 0 ) {
			int digit = (int)(x % baseTo.length());
			res = Character.toString(baseTo.charAt(digit)) + res;
			x = (int)( x / baseTo.length() );
		}
	}
	return res;
    }

}
