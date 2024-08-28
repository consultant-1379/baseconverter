package com.ericsson.maven.plugins.baseconverter;

/**
 */
public class RBaseConverter
{
    private static final String RBASE = "ABCDEFGHJKLMNSTUVXYZ"; 
	// Less I, O, P, Q, R  and W - special char set

    public static int rbase2int( String value ) 
    {
    	int x = 0;
    	for ( int i=0; i<value.length(); i++ ) {
    		x = x * RBASE.length() + RBASE.indexOf( value.charAt(i) ) + 1;
    	}
    	return x-1;
    }

    public static String int2rbase( int x )
    {
    	String res = "";
    	x = Math.abs(x)+1;	// We only deal with natural range (0..) shifted by 1
    	while ( x > 0 ) {
    		// (1-1)%3=0(A), (2-1)%3=1(B), (3-1)%3=2(A)
    		// we need to have 0 base though when calculating our numbers
    		int digit = (int)( (x-1) % RBASE.length());  
    		res = Character.toString( RBASE.charAt(digit) ) + res;
    		x = (int)( (x-1) / RBASE.length() );
    	}
    	return res;
    }

}
