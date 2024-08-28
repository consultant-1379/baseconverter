package com.ericsson.maven.plugins.baseconverter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RBaseConverterTest extends TestCase {

	public RBaseConverterTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RBaseConverterTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testRBaseConverter()
    {
    	int x; String s;
    	s = RBaseConverter.int2rbase(0);	
    	assertEquals( "A", s );
    	
    	s = RBaseConverter.int2rbase(5);	
    	assertEquals( "F", s );

    	s = RBaseConverter.int2rbase(20);	
    	assertEquals( "AA", s );

    	s = RBaseConverter.int2rbase(12345);
    	x = RBaseConverter.rbase2int(s);	
    	assertEquals( 12345, x );
    }
}
