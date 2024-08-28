package com.ericsson.maven.plugins.baseconverter;

/**
 * Hello world!
 *
 */
public class ConverterR2M
{
    public static void main( String[] args )
    {
	if ( args.length > 0 ) {
        	System.out.println( RstateConverter.convertRstate2Numeric( args[0] ));
		System.exit(0);
	} else {
        	System.out.println( "Please provide a valid RSTATE version number parameter.");
		System.exit(1);
	}
    }
}
