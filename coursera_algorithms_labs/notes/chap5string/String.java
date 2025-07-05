//package chap5string;
//
///*
//* String data type (in Java).
//* Sequence of characters (immutable).
//* Underlying implementation. Immutable char[] array, offset, and length.
//*
//* StringBuilder data type.
//* Sequence of characters (mutable).
//* Underlying implementation. Resizing char[] array and length.
//*
//* Remark. StringBuffer data type is similar, but thread safe (and slower).
//* */
//public final class String implements Comparable<String> {
//    private char[] value; // characters， Immutable char[] array
//    private int offset; // index of first char in array
//    private int length; // length of string
//    private int hash; // cache of hashCode()
//
//    public int length() {
//        return length;
//    }
//
//    public char charAt(int i) {
//        return value[i + offset];
//    }
//
//    private String(int offset, int length, char[] value) {
//        this.offset = offset;
//        this.length = length;
//        this.value = value;
//    }
//
//    // copy of reference to original char array
//    public String substring(int from, int to) {
//        return new String(offset + from, to - from, value);
//    }
//}