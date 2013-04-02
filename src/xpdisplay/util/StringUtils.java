/*
 * Copyright (c) Duncan Jauncey 2013.   Free for non-commercial use.
 */

package xpdisplay.util;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static char STRING_SEPARATOR = ':';
    private static String DATE_FORMAT = "dd/MM/yy HH:mm:ss";
    private static String TIME_FORMAT = "HH:mm:ss";

    protected StringUtils() {
        // you cannot instantiate one of these, you have to use the static methods.
    }
    
    public static boolean isNullOrEmpty(String s) {
        return( s == null || s.length() == 0 );
    }

    public static String removeSpaces(String in) {
        if( in == null ) { return null; }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < in.length(); ++i) {
            char c = in.charAt(i);
            int n = (int) c;
            if (n != 32) {
                out.append(c);
            }
        }
        return out.toString();
    }

    public static String removeCommas(String in) {
        if( in == null ) { return null; }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < in.length(); ++i) {
            char c = in.charAt(i);
            int n = (int) c;
            if (n != 44) {
                out.append(c);
            }
        }
        return out.toString();
    }

    public static String removeNormalCharacters(String in) {
        if( in == null ) { return null; }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < in.length(); ++i) {
            char c = in.charAt(i);
            int n = (int) c;

            if (n < 32 || n > 126) {
                out.append(c);
            }
        }
        return out.toString();
    }

    public static String removeNonNumericCharacters(String in) {
        if( in == null ) { return null; }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < in.length(); ++i) {
            char c = in.charAt(i);
            int n = (int) c;

            if (n >= 48 && n <= 57) {
                out.append(c);
            }
        }
        return out.toString();
    }


    public static List wrap(String s) {
        return wrap(s, 80);
    }

    public static List wrap(String s, int nWrapAt) {
        return wrap(s, nWrapAt, 0);
    }

    public static List wrap(String s, int nWrapAt, int nWrapTo) {
        List lines = new ArrayList();

        int width = nWrapAt;
        boolean bIsWrappable = true;
        while (bIsWrappable && s.length() > width) {
            // divide up
            int nSpacePos = s.lastIndexOf(' ', width);
            if (nSpacePos > 0) {
                String output = s.substring(0, nSpacePos);
                //System.out.println( "|"+output+"|" );
                lines.add(output);
                s = StringUtils.spaces(nWrapTo) + s.substring(nSpacePos + 1);
                //System.out.println( "|"+s+"|" );
                //System.out.println();
            } else {
                bIsWrappable = false;
            }
        }
        lines.add(s);

        return lines;
    }

    public static String replaceAll(String sSource, String sPattern, String sReplacement) {
        if (sSource == null || sSource.equals("")) {
            return sSource;
        }

        String sResult = new String(sSource);

        int i = sResult.indexOf(sPattern);
        while (i != -1) {
            String sFront = sResult.substring(0, i);
            String sBack = sResult.substring(i + sPattern.length());

            sResult = sFront + sReplacement + sBack;

            i = sResult.indexOf(sPattern);
        }

        return sResult;
    }

    public static String currentTime() {
        return formatTime(Calendar.getInstance());
    }

    public static String formatElapseTime(long ms) {
        int timeLeft = (int) (ms - System.currentTimeMillis());
        return formatTimePeriod(timeLeft);
    }

    public static String formatTimePeriod(int ms) {
        int seconds = (int) (ms / 1000) % 60;
        int minutes = (int) (ms / (1000 * 60)) % 60;
        int hours = (int) (ms / (1000 * 60 * 60)) % 24;
        int days = (int) (ms / (1000 * 60 * 60 * 24));

        return StringUtils.formatTimePeriod(days, hours, minutes, seconds);
    }

    public static String formatTimePeriod(int days, int hours, int minutes, int seconds) {
        String result = "";

        if (days > 0) {
            result += "" + days + " day";
            if (days != 1)
                result += "s,";
            result += " ";
        }

        if (days > 0 || hours > 0) {
            result += "" + hours + " hour";
            if (hours != 1)
                result += "s,";
            result += " ";
        }

        if (days > 0 || hours > 0 || minutes > 0) {
            result += "" + minutes + " minute";
            if (minutes != 1)
                result += "s";
            result += " and ";
        }

        result += "" + seconds + " second";

        if (seconds != 1)
            result += "s";

        return result;
    }

    public static String formatDate(Calendar c) {
        if (c == null)
            return null;

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(c.getTime());
    }

    public static String formatDate(Date c) {
        if (c == null)
            return null;

        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(c);
    }

    public static String formatTime(Calendar c) {
        if (c == null)
            return null;

        SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
        return df.format(c.getTime());
    }

   public static String formatTime(Date d) {
        if (d == null)
            return null;

        SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
        return df.format(d);
    }

    public static String collateStrings(List list, boolean bTrimWhitespace) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < list.size(); ++i) {
            String s = (String) list.get(i);

            if (bTrimWhitespace)
                s = s.trim();

            sb.append(s);
        }

        return sb.toString();
    }

    public static String substringBetween(String s, String part1, String part2) {
        String sub = null;

        int i = s.indexOf(part1);
        int j = s.indexOf(part2, i + part1.length());

        if (i != -1 && j != -1) {
            int nStart = i + part1.length();
            sub = s.substring(nStart, j);
        }

        return sub;
    }

    public static String centreString(String s) {
        return centreString(s, 80);
    }

    public static String centreString(String s, int w) {
        int len = s.length();
        if (len >= w)
            return s;

        int gap = (w - len) / 2;

        StringBuffer result = new StringBuffer(80);
        for (int i = 0; i < gap; ++i) {
            result.append(" ");
        }
        result.append(s);

        return result.toString();
    }

    public static String spaces(int n) {
        String s = "";
        return spaces(s, n);
    }

    public static String spaces(String s, int n) {
        return repeatChar(' ', s, n);
    }

    public static String chars(char c, int n) {
        String s = "";
        return repeatChar(c, s, n);
    }

    public static String repeatChar(char c, String s, int n) {
        for (int i = 0; i < n; ++i) {
            s += c;
        }
        return s;
    }

    // Pads a string s with spaces until its length is nLength, returns the result.
    public static String pad(String s, int nLength) {
        int n = nLength - s.length();
        if (n > 0) {
            s += spaces(n);
        }
        return s;
    }

    public static String fixLength(String s, int nLength) {
        String result = pad(s, nLength);
        if (result.length() > nLength) {
            result = result.substring(0, nLength);
        }
        return result;
    }


    public static String removeWord(String input, int i) {
        if (i <= 0)
            return input;


        StringBuffer s = new StringBuffer();

        int nLastSpacePos = 0;
        boolean bNotAtEnd = true;
        int nWordNum = 1;

        try {
            while (bNotAtEnd) {
                int nSpacePos = input.indexOf(' ', nLastSpacePos + 1);
                if (nSpacePos != -1) {
                    if (nWordNum != i)
                        s.append(input.substring(nLastSpacePos, nSpacePos));
                } else {
                    if (nWordNum != i)
                        s.append(input.substring(nLastSpacePos));

                    bNotAtEnd = false;
                }
                nLastSpacePos = nSpacePos;
                nWordNum++;
            }
        } catch (IndexOutOfBoundsException ioobe) {
            // went off the end of the string with our '+1's
            // do NOT need to add last word to string

            //System.out.println( "IOOBE at removeWord(), nLastSpacePos="+nLastSpacePos+", nWordNum="+nWordNum+", bNotAtEnd="+bNotAtEnd );
            //System.out.println( ioobe.getMessage() );
            //ioobe.printStackTrace( System.out );
        }


        return s.toString();
    }


    // remove the first occurance of the string from the input.
    public static String removeString(String input, String toRemove) {
        if (input == null || toRemove == null)
            return null;

//		System.out.println( " Removing |"+toRemove+"| from |"+input+"|" );
        int index = input.indexOf(toRemove);

        if (index == -1)
            return input;

        String newString = input.substring(0, index);

        int i = index + toRemove.length() + 1;

        String end = "";
        if (i < input.length())
            end = input.substring(index + toRemove.length());
//		System.out.println( "newstring=|"+newString+"|  END=|"+end+"|" );

        newString += end;

        return newString;
    }

    public static String extractLocationFilename(String s) throws IndexOutOfBoundsException {
        int i = s.indexOf(':');

        return s.substring(0, i);
    }

    public static int extractLocationID(String s) throws IndexOutOfBoundsException {
        int i = s.indexOf(':');

        return Integer.parseInt(s.substring(i + 1));
    }


    // extract strings from a colon-separated list
    public static List extractStrings(String s) {
        List strings = new ArrayList();

        if (s == null || s.equals("")) {
            return strings;
        }

        try {
            while (s.indexOf(STRING_SEPARATOR) != -1) {
                int nIndex = s.indexOf(STRING_SEPARATOR);
                strings.add(s.substring(0, nIndex));

                s = s.substring(nIndex + 1);
            }

            strings.add(s);
        } catch (IndexOutOfBoundsException ioobe) {
            // never mind.
        }

        return strings;
    }


    public static String checkNotNull(String s) throws Exception {
        if (s == null || s.equals("null"))
            throw new Exception("String is null.");

        return s;
    }

    public static String assemble(List strings) {
        return assemble(strings, ":");
    }

    public static String assemble(List strings, String sDelimiter) {
        String output = "";
        for (int i = 0; i < strings.size() - 1; ++i) {
            String s = (String) strings.get(i);
            output += s + sDelimiter;
        }

        if (strings.size() > 0)
            output += (String) strings.get(strings.size() - 1);

        return output;
    }

    public static String[] subArray(String[] strings, int nStart) {
        String[] out = new String[strings.length - nStart + 1];

        for (int i = nStart; i < strings.length; ++i) {
            out[i] = strings[i];
        }

        return out;
    }

    public static String assemble(String[] strings, String sDelimiter) {
        String output = "";
        for (int i = 0; i < strings.length - 1; ++i) {
            String s = strings[i];
            output += s + sDelimiter;
        }

        if (strings.length > 0)
            output += strings[strings.length - 1];

        return output;
    }

    public static int findNextLineWithString(List strings, int nStartIndex, String searchString) {
        int i = -1;
        try {
            int n = nStartIndex;
            while (n < strings.size() && i == -1) {
                String s = (String) strings.get(n);
                if (s.indexOf(searchString) != -1) {
                    i = n;
                }
                ++n;
            }
        } catch( ArrayIndexOutOfBoundsException e ) {
//            System.err.println("Array index out of bounds: findNextLineWithString(|"+strings.size()+"|,"+nStartIndex+","+searchString+")");
        }
        return i;
    }


    public static List getSubList(List source, int nFrom, int nTo)	// INCLUSIVE
    {
        if (nFrom < 0)		// $$$$ HACK
            nFrom = 0;

        List list = new ArrayList();
        for (int i = nFrom; i < (nTo + 1); ++i) {
            list.add(source.get(i));
        }
        return list;
    }



    public static String escapeText(String s) {
        StringBuffer text = new StringBuffer(s);

        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (c == '"') {
                text.insert(i, '\\');
                ++i;
            }
            ++i;
        }

        //System.out.println( "escapeText() Converted |"+s+"| to |"+text.toString()+"|" );

        return text.toString();
    }


    public static String unescapeText(String s) {
        StringBuffer text = new StringBuffer(s);

        boolean more = true;
        while (more) {
            // if JDK 1.4, swap the following for a performance increase.
            //int i = text.indexOf( "\\\"" );	// 1.4 only
            int i = text.toString().indexOf("\\\"");	// 1.3 only

            if (i != -1) {
                text.replace(i, i + 2, "\"");
            } else {
                more = false;
            }
        }

        //System.out.println( "unescapeText() Converted |"+s+"| to |"+text.toString()+"|" );

        return text.toString();
    }

    public static int getValue(String line, String parameter) throws NumberFormatException {
        String value = StringUtils.substringBetween(line, parameter + "=\"", "\"");
        return Integer.parseInt(value);
    }

    public static String getString(String line, String parameter) {
        String value = StringUtils.substringBetween(line, parameter + "=\"", "\"");
        return value;
    }

    public static String convertToString(Throwable e) {
        StringBuffer result = new StringBuffer();
        result.append(e.toString());
        StackTraceElement[] elements = e.getStackTrace();
        for( int i=0; i<elements.length; i++ ) {
            StackTraceElement element = elements[i];
            result.append("\n     at ");
            result.append(element.getClassName());
            result.append(".");
            result.append(element.getMethodName());
            result.append("(");
            result.append(element.getFileName());
            result.append(":");
            result.append(element.getLineNumber());
            result.append(")");
        }
        return result.toString();
    }
    
    public static String replaceMixedCaseWith(String line, String search, String replacement) {
        Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        return matcher.replaceAll(replacement);
    }
    
    public static String surroundSingleQuotes(String line, String pre, String post) {
        String patternStr = "\'([\\w ?]*)\'";
        String replaceStr = pre+"\'$1\'"+post;
        Pattern pattern = Pattern.compile(patternStr);
        line = pattern.matcher(line).replaceAll(replaceStr);        
        return line;
    }
    
    public static String surroundDoubleQuotes(String line, String pre, String post) {
        String patternStr = "\"([\\w ?]*)\"";
        String replaceStr = pre+"\"$1\""+post;
        Pattern pattern = Pattern.compile(patternStr);
        line = pattern.matcher(line).replaceAll(replaceStr);        
        return line;
    }    
    
    public static String removeDoublespacesExceptWithinLiterals(String text) {
        String literalPattern = "([\\'][\\w\n ]*[\\'])";

        // Compile and use regular expression
        Pattern pattern = Pattern.compile(literalPattern);
        Matcher matcher = pattern.matcher(text);
    
        List literals = new ArrayList();
        
        while (matcher.find()) {
            // Get all groups for this match
            String groupStr = matcher.group(0);
            literals.add(groupStr);
        }         
        
        int i = 0;
        for( Iterator it = literals.iterator(); it.hasNext(); ) {
            String literal = (String) it.next();
            text = text.replaceFirst(literal, "[LITERAL-"+i+"]");
            i++;
        }
        
        text = text.replaceAll("\\s+", " ");
        
        i = 0;
        for( Iterator it = literals.iterator(); it.hasNext(); ) {
            String literal = (String) it.next();
            text = text.replaceFirst("\\[LITERAL-"+i+"\\]", literal);
            i++;
        }
        
        return text;
    }
    
    public static String removeHtmlTags(String s) {
        if( s == null ) {
            return null;
        }
        
        StringBuffer result = new StringBuffer();
        int start = s.indexOf("<");
        int lastEnd = 0;
        while( start != -1 ) {
            int end = s.indexOf(">", start);
            if( end != -1 ) {
                // append
                result.append(s.substring(lastEnd, start));
                start = s.indexOf("<", end);
                lastEnd = end+1;
            } else {
                start = -1;
            }
            
        }
        return result.toString();        
        //return s.replaceAll("\\<.*?\\>", "");
    }
 
    
    public static String removeSubsection(String s, String start, String end) {
        if( s == null ) {
            return null;
        }

        boolean moreLeft = true;
        while( moreLeft ) {
            int startPos = s.indexOf(start);
            if( startPos == -1 ) {
                moreLeft = false;
                break;
            }
            int endPos = s.indexOf(end, startPos);
            if( endPos == -1 ) {
                moreLeft = false;
                break;
            }

            s = s.substring(0, startPos) + s.substring(endPos + end.length());
        }
        
        return s;
    }
    
    public static String collapseMultipleNewlinesToOne(String s) {
        return s.replaceAll("\\\n+", "\\\n");
    }
}

