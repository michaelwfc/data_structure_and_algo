package chap5string;


import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
//import edu.princeton.cs.algs4.RunLength;

public class RunLength {
    private final static int R=256; //maximum run-length count
    private final static int lgR=8;

    public static void compress(){
        char cnt=0;
        boolean b, old= false;
        while(!BinaryStdIn.isEmpty()){
            b= BinaryStdIn.readBoolean();
            if(b!=old){
                BinaryStdOut.write(cnt);
                cnt=0;
                old=!old;
            }else{
                if(cnt==255){
                    BinaryStdOut.write(cnt);
                    cnt=0;
                    BinaryStdOut.write(cnt); // ?
                }
                cnt++;
            }
        }
        BinaryStdOut.write(cnt);
        BinaryStdOut.close();

    }

    public static void expand(){
        boolean bit = false;
        while(!BinaryStdIn.isEmpty()){
            int run= BinaryStdIn.readInt(lgR); //read 8-bit count from standard input
            for(int i=0; i<run; i++)
                BinaryStdOut.write(bit); // write 1 bit to standard output
            bit= !bit;
        }
        BinaryStdOut.close(); //pad 0s for byte alignment
    }

    /*
    * byte: 8 bits
    * char: 8 bits
    * boolean: 1 bit
    * short: 16 bits/2 bytes
    * int: 32 bits/4 bytes
    * long: 64 bits
    * float: 32 bits
    * double: 64 bits
    * */
    public static void binaryPrint(){


    }
    public static void main(String[] args){
        binaryPrint();

    }

}
