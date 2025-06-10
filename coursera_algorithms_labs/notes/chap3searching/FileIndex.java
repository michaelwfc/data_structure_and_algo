package chap3searching;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.ArrayList;

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ST;

public class FileIndex
{
    public static void main(String[] args)
    {
        ST<String, SET<File>> st = new ST<String, SET<File>>();
//        for (String filename : args) {
        List<String> filenameList = getFiles(args);
        for(String filename: filenameList){
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty())
            {
                String key = in.readString();
                if (!st.contains(key))
                    st.put(key, new SET<File>());
                SET<File> set = st.get(key);
                set.add(file);
            }
        }
        while (!StdIn.isEmpty())
        {
            String query = StdIn.readString();
            StdOut.println(st.get(query));
        }
    }

    public static List<String> getFiles(String[] args) {
        File folder = new File("algs4-data");

        // Filter files starting with "ex" and ending with ".txt"
        FilenameFilter filter = (dir, name) -> name.startsWith("ex") && name.endsWith(".txt");

        File[] matchingFiles = folder.listFiles(filter);
        List<String> filenameList = new  ArrayList<String>();

        if (matchingFiles != null) {
            for (File file : matchingFiles) {
                System.out.println(file.getPath());
                String filename = file.getPath();
                filenameList.add(filename);
            }
            return filenameList;
        } else {
            System.out.println("Directory does not exist or an I/O error occurred.");
            return null;
        }

    }
}