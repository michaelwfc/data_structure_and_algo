- https://coursera.cs.princeton.edu/algs4/assignments/hello/specification.php
- https://lift.cs.princeton.edu/java/windows/
  



# Use the following library functions from algs4.jar
- StdIn.readString(): reads and returns the next string from standard input.
- StdIn.isEmpty(): returns true if there are no more strings available on standard input, and false - otherwise.
- StdOut.println(): prints a string and terminating newline to standard output. It’s also fine to use System.out.println() instead.
- StdRandom.bernoulli(p): returns true with probability p and false with probability 1−p


  In order to access these library functions, you must do the following two things:

1. Add algs4.jar to the Java classpath. This typically requires a different mechanism from the command line and the IDE.
- If you used our autoinstaller, the Bash commands javac-algs4 and java-algs4 add algs4.jar to the Java classpath.
- If you use IntelliJ, the supplied IntelliJ project folder includes algs4.jar and adds it to the Java classpath.
- If you prefer to use some other shell (such as Powershell or zsh) or IDE (such as Eclipse or Netbeans), that’s fine—just be sure that you can configure it accordingly.

2. Add an import statement like the following at the top of your program:
```java
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
```
If you use IntelliJ and the provided project folder, IntelliJ will automatically add and remove import statements as needed, so you won’t need to type them.