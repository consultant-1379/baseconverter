/**
 *  Test Cases for RstateConverter
 */
package com.ericsson.maven.plugins.baseconverter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Pavel Smirnov
 * 
 */
public class RstateConverterTest extends TestCase {

	public RstateConverterTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(RstateConverterTest.class);
	}

	public void testRstateConverterParser() {
		String r;
		String[] e;

		r = "R1A";
		e = RstateConverter.parseRstate(r);
		assertEquals("1", e[0]);
		assertEquals("A", e[1]);

		r = "R1A01";
		e = RstateConverter.parseRstate(r);
		assertEquals("1", e[0]);
		assertEquals("A", e[1]);
		assertEquals("01", e[2]);

		r = "R1A02_EC03";
		e = RstateConverter.parseRstate(r);
		assertEquals("1", e[0]);
		assertEquals("A", e[1]);
		assertEquals("02", e[2]);
		assertEquals("03", e[3]);

	}

	public void testRstateConverterR2MFormatter() {
		String r;
		String m;

		r = "R1A";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.0", m);

		r = "R1A-SNAPSHOT";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.0-SNAPSHOT", m);

		r = "R1A02";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.0.2", m);

		r = "R1A02-SNAPSHOT";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.0.2-SNAPSHOT", m);

		r = "R1B02_EC07";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.1.2.007", m);

		r = "R1B02_EC07SNAPSHOT";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.1.2.007-SNAPSHOT", m);

		r = "R1B02_EC07-SNAPSHOT";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.1.2.007-SNAPSHOT", m);

		r = "R1B02_EC007-SNAPSHOT";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.1.2.007-SNAPSHOT", m);

		r = "R1B02_EC7-SNAPSHOT";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("1.1.2.007-SNAPSHOT", m);

		// Considering A==0, AB means 0(A)*20 + 1(B), likewise AA=0
		// So basically any number of leading "A"s is equivalent to leading "0"s
		// This is important - we need to validate it against the Rstate
		// standards
		r = "R44AB02_EC07";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("44.21.2.007", m);

		// BB = 20*1(B) + 1(B) = 21
		r = "R44BB02_EC07";
		m = RstateConverter.convertRstate2Numeric(r);
		assertEquals("44.41.2.007", m);

	}

	public void testRstateConverterM2RFormatter() {
		String r;
		String m;

		m = "1.0.1.1";
		r = RstateConverter.convertNumeric2Rstate(m);
		assertEquals("R1A01_EC001", r);

		m = "1.0";
		r = RstateConverter.convertNumeric2Rstate(m);
		assertEquals("R1A", r);

		m = "1.2.3";
		r = RstateConverter.convertNumeric2Rstate(m);
		assertEquals("R1C03", r);

		m = "1.2-SNAPSHOT";
		r = RstateConverter.convertNumeric2Rstate(m);
		assertEquals("R1C_SNAPSHOT", r);

		m = "1.2.3-SNAPSHOT";
		r = RstateConverter.convertNumeric2Rstate(m);
		assertEquals("R1C03_SNAPSHOT", r);

		m = "1.2.3.4-SNAPSHOT";
		r = RstateConverter.convertNumeric2Rstate(m);
		assertEquals("R1C03_EC004_SNAPSHOT", r);

		m = "44.21.5.777";
		r = RstateConverter.convertNumeric2Rstate(m);
		assertEquals("R44AB05_EC777", r);

	}

}
