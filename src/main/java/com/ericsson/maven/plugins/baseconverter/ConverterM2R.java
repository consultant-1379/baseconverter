package com.ericsson.maven.plugins.baseconverter;

/**
 * Hello world!
 *
 */
public class ConverterM2R
{
    public static void main( String[] args )
    {
	if ( args.length > 0 ) {
        	System.out.println( RstateConverter.convertNumeric2Rstate( args[0] ));
		System.exit(0);
	} else {
        	System.out.println( "Please provide a valid N.N[.N[.N]] version number parameter.");
		System.exit(1);
	}
    }
}
