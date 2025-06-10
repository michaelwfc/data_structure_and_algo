package chap4graphs;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WebCrawler {
    public static void main(){
        Queue<String> queue =  new Queue<String>();  //queue of websites to crawl
        SET<String> marked = new SET<String>();      //set of marked websites

        String root =  "http://www.princeton.edu";
        queue.enqueue(root);  //start crawling from root website
        marked.add(root);

        while(!queue.isEmpty()){
            String v = queue.dequeue();
            StdOut.println(v);
            In in = new In(v); // read in raw html from next  website in queue
            String input = in.readAll();

            String regexp ="http://(\\w+\\.)8(\\w+)"; // use regular expression to find all URLs in website of form http://xxx.yyy.zzz [crude pattern misses relative URLs]
            Pattern patter = Pattern.compile(regexp);
            Matcher matcher = patter.matcher(input);
            while (matcher.find()){
                String w = matcher.group();
                if(!marked.contains(w)){
                    marked.add(w); //if unmarked, mark it and put  on the queue
                    queue.enqueue(w);
                }
            }
        }


    }
}
