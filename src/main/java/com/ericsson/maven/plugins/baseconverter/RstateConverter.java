/**
 *  RstateConverter class for parsing between Maven N.N.N.N and RNNAANN_XXNN Rstate version numbers
 */
package com.ericsson.maven.plugins.baseconverter;

//import java.text.DecimalFormat;
//import java.text.NumberFormat;
import java.util.regex.*;

/**
 * @author Pavel Smirnov
 * 
 */
public class RstateConverter {

    // RN[A[N[_anythingN[-?anything]]]]
    // private static String RSTATE_FORMAT = "R([0-9]+)(([A-Z]+)(([0-9]+)(_[A-Z]*([0-9]+)(-?(.+)?)?)?)?)?";
    private static String RSTATE_FORMAT = "R([0-9]+)(([A-Z]+)(([0-9]+)(_[A-Z]*([0-9]+))?)?)?(\\-?(.+))?";
    //                                      1       23_______45______ 6       7______       8  9__
    private static int[] RSTATE_SEGMENTS = {1,3,5,7,9};

    // N[.N[.N[.N]]][-anything]
    // private static String MAVEN_FORMAT = "([0-9]+)(\\.([0-9]+)(\\.([0-9]+)(\\.([0-9]+)(-(.+)?)?)?)?)?";
    private static String MAVEN_FORMAT = "([0-9]+)(\\.([0-9]+)(\\.([0-9]+)(\\.([0-9]+))?)?)?(\\-(.+))?";
    //                                    1       2   3_______4   5______ 6   7______       8 9__
    private static int[] MAVEN_SEGMENTS = {1,3,5,7,9};

    // We will parse minimum of two and maximum four segments in a version
    // number
    // We are going to discard anything after dash

    public static String[] parseRstate(String rstate) {
	return parseString(rstate, RSTATE_FORMAT, RSTATE_SEGMENTS);
    }

    public static String[] parseMaven(String numeric) {
	return parseString(numeric, MAVEN_FORMAT, MAVEN_SEGMENTS);
    }

    public static String[] parseString(String in, String patternStr, int[] segments) {
	String[] elements;
	// Compile and use regular expression
	Pattern pattern = Pattern.compile(patternStr);
	Matcher matcher = pattern.matcher(in);
	boolean matchFound = matcher.find();
	if (matchFound) {
	    /*
	    // This is for debug only
	    for (int i=0;i<matcher.groupCount();i++) {
	    System.out.println( Integer.toString(i)+":" + matcher.group(i));
	    }
	    */
	    // Extract only segments of interest and only if they are found
	    elements = new String[ segments.length ];
	    int item = 0;
	    for (int i : segments ) {
		if (i > matcher.groupCount()) {	
		    break; // No such group identified, no reason to continue
		}
		// if ( matcher.group(i) == null ) {
		//	break; // Value is null, no reason to continue
		// }
		elements[item] = matcher.group(i);
		item++;
	    }
	} else {
	    elements = new String[0];
	}
	return elements;
    }

    public static String convertRstate2Numeric(String rstate) {
	// Note that format string above caters for 5 segments
	String[] parsed = RstateConverter.parseRstate(rstate);
	String result = "";
	for ( int i=0; i<parsed.length; i++) {
	    if (parsed[i]==null){ // Stop on empty segment
		continue;
	    }
	    int converted = 0; 
	    if ( i == 1) { 
		converted = RBaseConverter.rbase2int(parsed[i]);
		// Special treatment for second segment of RSTATE
	    } else if (i<4) {
		converted = Integer.parseInt(parsed[i]);
		// Numeric handling for 1..4
	    }
	    if (i==0){				// No delimiter needed yet, we start here
		result = Integer.toString(converted);
	    } else if ( i==3 ) {	// Special treatment for segment four requested by OSGi folks - 3 digits 
		result=result+"."+ String.format("%03d", converted);
	    } else if ( i<4 ) { 	// Anything up to fourth element is a number delimited with dot 
		result=result+"."+ Integer.toString(converted); 
	    } else {				// After that we use dashes after 4th segment and provide it as is
		result=result+"-"+ parsed[i]; 
	    }
	}
	return result;
    }

    public static String convertNumeric2Rstate(String numeric) {
	// Note that format string above caters
	String[] parsed = RstateConverter.parseMaven(numeric);

	String result = "R"; // We start with R
	//		NumberFormat formatter = new DecimalFormat("00");       // 2nd and 4th segment need leading zeroes      
	for (int i = 0; i<parsed.length; i++) {
	    if (parsed[i]==null){ // Skip empty segment
		continue;
	    }
	    if (i==0) {
		int converted = Integer.parseInt(parsed[i]); // We only expect numbers (no leading 0)
		result = result + Integer.toString(converted);
	    } else if (i==2) {	// First and third segments come w/o separators
		int converted = Integer.parseInt(parsed[i]); // We only expect numbers
		result = result + String.format("%02d", converted); // Adding Leading Zeros up to two digits
	    } else if (i==1) {	// Second segment to be converted with rbase
		int converted = Integer.parseInt(parsed[i]); // We only expect numbers
		result = result + RBaseConverter.int2rbase(converted);
	    } else if (i==3) { // Fourth segment to be prefixed with _EC
		int converted = Integer.parseInt(parsed[i]); // We only expect numbers
		result = result + "_EC" + String.format("%03d", converted); // Adding Leading Zeros 
	    } else { // Anything beyond it will be underscore prefixed (SNAPSHOTS can't have 
		result = result + "_" + parsed[i];
	    }
	}
	return result;
    }
}
