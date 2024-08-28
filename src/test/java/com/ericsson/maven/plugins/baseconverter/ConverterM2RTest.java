package com.ericsson.maven.plugins.baseconverter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple ConverterR2M.
 */
public class ConverterM2RTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ConverterM2RTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ConverterR2MTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testConverterR2M()
    {
        assertTrue( true );
    }
}
