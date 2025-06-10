//package chap3searching;
//
//import java.util.Date;
//
//
//public class Transaction {
//
//    private final String who;
//    private final Date when;
//    private final double amount;
//
//    // cache of hash code
//    private int hash = -1;
//
//    public int hashCode() {
//        int h = hash;
//        if (h != -1) return h; // return cached value
//
//        int hash = 17;
//        hash = 31 * hash + who.hashCode();
//        hash = 31 * hash + when.hashCode();
//        hash = 31 * hash
//                + ((Double) amount).hashCode();
//
//        hash = h; // store cache of hash code
//        return hash;
//    }
//
//}