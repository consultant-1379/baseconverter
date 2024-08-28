package com.ericsson.maven.plugins.baseconverter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BaseConverterTest extends TestCase {

	public BaseConverterTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BaseConverterTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testBaseConverter()
    {
    	int x; String s;
    	x=0;	
    	s = BaseConverter.int2base( x, BaseConverter.BASE2 );	
    	assertEquals( "0", s );
    	
    	x=10;			
    	s = BaseConverter.int2base( x, BaseConverter.BASE10 );	
    	assertEquals( "10", s );
    	
    	x=179791990; // Lets test with a fairly big number	
    	s = BaseConverter.int2base( x, BaseConverter.BASE16 );	
    	assertEquals( "AB76876", s );
    	// And now reverse it
    	x = BaseConverter.base2int( s, BaseConverter.BASE16 );	
    	assertEquals( 179791990, x );
    	
    	x=179791990; // Lets test with a fairly big number	
    	s = BaseConverter.int2base( x, BaseConverter.BASE2 );	
    	assertEquals( "1010101101110110100001110110", s );
    	// And now reverse it
    	x = BaseConverter.base2int( s, BaseConverter.BASE2 );	
    	assertEquals( 179791990, x );
    }
}
