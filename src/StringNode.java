/*
 * StringNode.java
 *
 * Computer Science E-22
 * Modified by: <your name>, <your e-mail address>
 */

import java.io.*;
import java.util.*;

/**
 * A class for representing a string using a linked list.  Each
 * character of the string is stored in a separate node.  
 *
 * This class represents one node of the linked list.  The string as a
 * whole is represented by storing a reference to the first node in
 * the linked list.  The methods in this class are static methods that
 * take a reference to a string linked-list as a parameter.  This
 * approach allows us to use recursion to write many of the methods.
 */
public class StringNode {
    private char ch;
    private StringNode next;

    /**
     * Constructor
     */
    public StringNode(char c, StringNode n) {
        ch = c;
        next = n;
    }

    /**
     * getNode - private helper method that returns a reference to
     * node i in the given linked-list string.  If the string is too
     * short, returns null.
     */
    private static StringNode getNode(StringNode str, int i) {
        if (i < 0 || str == null) {
            return null;
        }

        int counter = 0;

        StringNode trav = str;

        while(trav != null) {
            if (counter == i) {
                return trav;
            } else {
                counter++;
                trav = trav.next;
            }
        }

        return null;
    }

    /*****************************************************
     * Public methods (in alphabetical order)
     *****************************************************/

    /**
     * charAt - returns the character at the specified index of the
     * specified linked-list string, where the first character has
     * index 0.  If the index i is < 0 or i > length - 1, the method
     * will end up throwing an IllegalArgumentException.
     */
    public static char charAt(StringNode str, int i) {
        if (str == null)
            throw new IllegalArgumentException("the string is empty");

        StringNode node = getNode(str, i);

        if (node != null)
            return node.ch;
        else
            throw new IllegalArgumentException("invalid index: " + i);
    }

    /**
     * compareAlpha - compares two linked-list strings to determine
     * which comes first alphabetically (i.e., according  to the ordering 
     * used for words in a dictionary). 
     *
     * It returns:
     *    1 if str1 comes first alphabetically
     *    2 if str2 comes first alphabetically
     *    0 if str1 and str2 represent the same string
     *
     * The empty string comes before any non-empty string, 
     * and the prefix of a string comes before the string
     * itself (e.g., "be" comes before "become").
     */

    public static int compareAlpha(StringNode str1, StringNode str2) {
        StringNode trav1 = str1;
        StringNode trav2 = str2;

        while(trav1 != null && trav2 != null) {
            if(trav1.ch < trav2.ch) {
                return 1;
            } else if(trav2.ch < trav1.ch) {
                return 2;
            } else {
                trav1 = trav1.next;
                trav2 = trav2.next;
            }
        }

        if(trav1 ==  null && trav2 == null) {
            return 0;
        }
        else if(trav1 == null) {
            return 1;
        } else if(trav2 == null) {
            return 2;
        }

        return 0;
    }

        /**
         * concat - returns the concatenation of two linked-list strings
         */
    public static StringNode concat(StringNode str1, StringNode str2) {
        StringNode cat;

        if (str1 == null) {
            cat = copy(str2);
        } else if(str2 == null) {
            cat = copy(str1);
        } else {
            cat = copy(str1);
            StringNode lastNode = lastNode(cat);
            lastNode.next = copy(str2);
        }

        return cat;
    }

    public static StringNode lastNode(StringNode str) {
        StringNode trav = str;
        while(trav.next != null) {
            trav = trav.next;
        }
        return trav;
    }

    /**
     * convert - converts a standard Java String object to a linked-list
     * string and returns a reference to the linked-list string
     */
    public static StringNode convert(String s) {
        if (s == null || s.length() == 0)
            return null;

        StringNode firstNode = new StringNode(s.charAt(0), null);
        StringNode prevNode = firstNode;
        StringNode nextNode;

        for (int i = 1; i < s.length(); i++) {
            nextNode = new StringNode(s.charAt(i), null);
            prevNode.next = nextNode;
            prevNode = nextNode;
        }

        return firstNode;
    }

    /**
     * copy - returns a copy of the given linked-list string
     */

    public static StringNode copy(StringNode str) {
        if (str == null) {
            return null;
        }

        StringNode newFirst = null;
        StringNode trav = str;
        StringNode prevNode = null;

        while(trav != null) {
            StringNode newNode = new StringNode(trav.ch,  null);
            if (prevNode == null) {
               newFirst = newNode;
            } else {
                prevNode.next = newNode;
            }
            prevNode = newNode;
            trav = trav.next;
        }

        return newFirst;
    }

    /**
     * deleteChar - deletes character i in the given linked-list string and
     * returns a reference to the resulting linked-list string
     */
    public static StringNode deleteChar(StringNode str, int i) {
        if (str == null)
            throw new IllegalArgumentException("string is empty");
        else if (i < 0)
            throw new IllegalArgumentException("invalid index: " + i);
        else if (i == 0)
            str = str.next;
        else {
            StringNode prevNode = getNode(str, i-1);
            if (prevNode != null && prevNode.next != null)
                prevNode.next = prevNode.next.next;
            else
                throw new IllegalArgumentException("invalid index: " + i);
        }

        return str;
    }

    /**
     * indexOf - returns the position of the first occurrence of
     * character ch in the given linked-list string.  If there is
     * none, returns -1.
     */
    public static int indexOf(StringNode str, char ch) {
        if (str == null) {   // base case 1: ch wasn't found
            return -1;
        }

        StringNode trav = str;

        int currentIndex = 0;

        while(trav != null){
            if(trav.ch == ch){
                return currentIndex;
            }
            currentIndex++;
            trav = trav.next;
        }
        return -1;
    }

    /**
     * insertChar - inserts the character ch before the character
     * currently in position i of the specified linked-list string.
     * Returns a reference to the resulting linked-list string.
     */
    public static StringNode insertChar(StringNode str, int i, char ch) {
        StringNode newNode, prevNode;

        if (i < 0)
            throw new IllegalArgumentException("invalid index: " + i);
        else if (i == 0) {
            newNode = new StringNode(ch, str);
            str = newNode;
        } else {
            prevNode = getNode(str, i-1);
            if (prevNode != null) {
                newNode = new StringNode(ch, prevNode.next);
                prevNode.next = newNode;
            } else
                throw new IllegalArgumentException("invalid index: " + i);
        }

        return str;
    }

    /**
     * insertSorted - inserts character ch in the correct position
     * in a sorted list of characters (i.e., a sorted linked-list string)
     * and returns a reference to the resulting list.
     */
    public static StringNode insertSorted(StringNode str, char ch) {
        StringNode newNode, trail, trav;

        // Find where the character belongs.
        trail = null;
        trav = str;
        while (trav != null && trav.ch < ch) {
            trail = trav;
            trav = trav.next;
        }

        // Create and insert the new node.
        newNode = new StringNode(ch, trav);
        if (trail == null) {
            // We never advanced the prev and trav references, so
            // newNode goes at the start of the list.
            str = newNode;
        } else
            trail.next = newNode;

        return str;
    }

    /**
     * length - recursively determines the number of characters in the
     * linked-list string to which str refers
     */
    public static int length(StringNode str) {
        int counter = 0;

        StringNode trav = str;

        while(trav != null) {
            counter++;
            trav = trav.next;
        }

        return counter;
    }

    /**
     * numOccurrences - find the number of occurrences of the character
     * ch in the linked list to which str refers
     */
    public static int numOccurrences(StringNode str, char ch) {
        if (str == null)
            return 0;

        int numOccur = numOccurrences(str.next, ch);
        if (str.ch == ch)
            numOccur++;

        return numOccur;
    }

    /**
     * print - recursively writes the specified linked-list string to System.out
     */
    public static void print(StringNode str) {
        StringNode trav = str;

        while(trav != null) {
            System.out.print(trav.ch);
            trav = trav.next;
        }
    }

    /**
     * read - reads a string from an input stream and returns a
     * reference to a linked list containing the characters in the string
     */
    public static StringNode read(InputStream in) throws IOException {
        StringNode str;
        char ch = (char)in.read();

        if (ch == '\n')    // base case
            str = null;
        else
            str = new StringNode(ch, read(in));

        return str;
    }

    /*
     * substring - creates a new linked list that represents the substring 
     * of str that begins with the character at index start and has
     * length (end - start). It thus has the same behavior as the
     * substring method in the String class.
     * 
     * Throws an exception if start < 0, end < start, or 
     * if start and/or end > the length of the string.
     * 
     * Note that our method does NOT need to call the length()
     * method to determine if start and/or end > the length, and neither
     * should your revised method.
     */
    public static StringNode substring(StringNode str, int start, int end) {
        // Check for invalid parameters.
        if (start < 0 || end < start)
            throw new IndexOutOfBoundsException();

        if (str == null)         // end > length
            throw new IndexOutOfBoundsException();

        StringNode startNode = getNode(str, start);
        StringNode subStringStart = new StringNode(startNode.ch, null);
        StringNode prevSubString = subStringStart;
        StringNode travOriginalString = startNode.next;

        for (int i = start; i < end - 1; i++) {
            StringNode copy = new StringNode(travOriginalString.ch, null);
            prevSubString.next = copy;
            prevSubString = prevSubString.next;
            travOriginalString = travOriginalString.next;
        }


        return subStringStart;
    }

    /*
     * toString - creates and returns the Java string that
     * the current StringNode represents.  Note that this
     * method -- unlike the others -- is a non-static method.
     * Thus, it will not work for empty strings, since they
     * are represented by a value of null, and we can't use
     * null to invoke this method.
     */
    public String toString() {
        String str = "";
        StringNode trav = this;   // start trav on the current node

        while (trav != null) {
            str = str + trav.ch;
            trav = trav.next;
        }

        return str;
    }

    /**
     * toUpperCase - converts all of the characters in the specified
     * linked-list string to upper case.  Modifies the list itself,
     * rather than creating a new list.
     */
    public static void toUpperCase(StringNode str) {
        StringNode trav = str;
        while (trav != null) {
            trav.ch = Character.toUpperCase(trav.ch);
            trav = trav.next;
        }
    }

    public static void printWithHyphens(StringNode str) {
        if(str == null) {
            System.out.print("");
            return;
        }

        if(str.next == null) {
            System.out.print(str.ch);
        } else {
            System.out.print(str.ch);
            System.out.print("-");
        }

        printWithHyphens(str.next);
    }

    public static int numDiff(StringNode str1, StringNode str2) {
        if(str1 == null && str2 == null) {
            return 0;
        } else if(str1 == null) {
            return length(str2);
        } else if(str2 == null) {
            return length(str1);
        }

        int numDiffInRest = numDiff(str1.next, str2.next);
        if(str1.ch == str2.ch) {
            return numDiffInRest;
        } else {
            return 1 + numDiffInRest;
        }
    }

    public static int lastIndexOf(StringNode str, char ch) {
        if (str == null) {
            return -1;
        }

        int lastIndexInRest = lastIndexOf(str.next, ch);

        if(lastIndexInRest == -1) {
            // first occurrence found
            if(str.ch == ch) {
                return 0;
            } else {
                return -1;
            }
        } else {
            // already found somewhere later in string
            return 1 + lastIndexInRest;
        }
    }

    public static StringNode reverseInPlace(StringNode str){
        if(str == null) {
            return null;
        }

        StringNode newFirst = null;
        StringNode nextNode;
        StringNode prev = null;
        StringNode trav = str;

        while(trav != null) {
            nextNode = trav.next;
            trav.next = prev;
            prev = trav;
            trav = nextNode;
            if(nextNode == null) {
                newFirst = prev;
            }
        }

        return newFirst;
    }


    public static void main(String[] args) throws IOException {
        StringNode str1, str2, str3;
        String line;
        String s;
        int n;
        char ch;


        // length
        System.out.println("testing length");
        StringNode l1 = StringNode.convert("hello");
        // should print 5
        System.out.println(length(l1));
        // should print 0
        System.out.println(length(null));
        // should print 1
        StringNode l2 = StringNode.convert("h");
        System.out.println(length(l2));

        // indexOf
        System.out.println("testing indexOf");
        str1 = StringNode.convert("java");
        // should print 2
        System.out.println(indexOf(str1, 'v'));
        // should print -1
        System.out.println(indexOf(str1, 'z'));
        // should print 1
        System.out.println(indexOf(str1, 'a'));
        // should print -1 for null input of stringnode
        System.out.println(indexOf(null, 'a'));


        // print
        System.out.println("testing print");
        str1 = StringNode.convert("hello world");
        // prints "hello world"
        print(str1);
        // nothing is printed
        print(null);
        // nothing is printed
        str2 = StringNode.convert("");
        print(str2);
        System.out.println();

        // compareAlpha
        System.out.println("testing compareAlpha");
        str1 = StringNode.convert("become");
        str2 = StringNode.convert("be");
        str3 = StringNode.convert("orange");
        StringNode str4 = StringNode.convert("orange");
        // should print 2
        System.out.println(compareAlpha(str1, str2));
        // should print 1
        System.out.println(compareAlpha(str2, str3));
        // should print 0 for same character contents
        System.out.println(compareAlpha(str3, str4));
        // should print 2 since second arg null
        System.out.println(compareAlpha(str2, null));
        // should print 1 since first arg null
        System.out.println(compareAlpha(null, str1));
        // should print 0 for two null args
        System.out.println(compareAlpha(null, null));

        // copy
        System.out.println("testing copy");
        str1 = StringNode.convert("banana");
        str2 = copy(str1);
        // prints "banana"
        print(str2);
        System.out.println();
        // check that referance to start of str1 is not equal to ref to start of str2 so it's actually its own copy of the string, should print false
        System.out.println(str1 == str2);

        // concat
        System.out.println("testing concat");
        str1 = StringNode.convert("hello, ");
        str2 = StringNode.convert("world");
        // prints "hello, world"
        str3 = concat(str1, str2);
        print(str3);
        System.out.println();
        // prints "world", first arg is null
        str3 = concat(null, str2);
        print(str3);
        System.out.println();
         // prints "world", second arg is null
        str3 = concat(str2, null);
        print(str3);
        System.out.println();
        // check that str3 is null by checking equality to null, should be true
        str3 = concat(null, null);
        System.out.println(str3 == null);

        // substring
        System.out.println("testing substring");
        str1 = StringNode.convert("hello");
        StringNode subStr = str1.substring(str1, 2, 4);
        // prints "ll"
        System.out.println(subStr);


        // testing numDiff
        System.out.println("testing numDiff");
        StringNode s1 = StringNode.convert("sing");
        StringNode s2 = StringNode.convert("ring");
        StringNode s3 = StringNode.convert("sink");
        StringNode s4 = StringNode.convert("singing");
        // should print 1
        System.out.println(StringNode.numDiff(s1, s2));
        // should print 2
        System.out.println(StringNode.numDiff(s2, s3));
        // should print 3
        System.out.println(StringNode.numDiff(s1, s4));
        // should print 4
        System.out.println(StringNode.numDiff(s4, s2));
        // should print 4, first arg is null
        System.out.println(StringNode.numDiff(null, s2));
        // should print 7, second arg is null
        System.out.println(StringNode.numDiff(s4, null));

        // testing lastIndexOf
        System.out.println("testing last index of");
        str1 = StringNode.convert("singing");
        str2 = StringNode.convert("hi");
        str3 = StringNode.convert("pepper");
        // should print 0
        System.out.println(StringNode.lastIndexOf(str2, 'h'));
        // should print 1
        System.out.println(StringNode.lastIndexOf(str2, 'i'));
        // should print 4 for last occurrence of n in singing
        System.out.println(StringNode.lastIndexOf(str1, 'n'));
        // should print 3 for last occurrence of p in pepper
        System.out.println(StringNode.lastIndexOf(str3, 'p'));
        // should print -1 for not found
        System.out.println(StringNode.lastIndexOf(str1, 'z'));
        // null should give -1
        System.out.println(StringNode.lastIndexOf(null, 'z'));

        // testing reverseInPlace
        System.out.println("testing reverseInPlace");
        str1 = StringNode.convert("singing");
        str2 = reverseInPlace(str1);
        str3 = StringNode.convert("a");
        // should print gnignis
        System.out.println(str2);
        // should print null
        System.out.println(reverseInPlace(null));
        // should print a
        System.out.println(reverseInPlace(str3));
    }
}