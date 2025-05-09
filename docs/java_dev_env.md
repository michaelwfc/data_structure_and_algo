- https://coursera.cs.princeton.edu/algs4/assignments/hello/specification.php
- https://lift.cs.princeton.edu/java/windows/
  



# Use the following library functions from algs4.jar
- StdIn.readString(): reads and returns the next string from standard input.
- StdIn.isEmpty(): returns true if there are no more strings available on standard input, and false - otherwise.
- StdOut.println(): prints a string and terminating newline to standard output. It’s also fine to use System.out.println() instead.
- StdRandom.bernoulli(p): returns true with probability p and false with probability 1−p


  In order to access these library functions, you must do the following two things:

1. Add algs4.jar to the Java classpath. 
   
   This typically requires a different mechanism from the command line and the IDE.
- If you used our autoinstaller, the Bash commands javac-algs4 and java-algs4 add algs4.jar to the Java classpath.

- If you use IntelliJ, the supplied IntelliJ project folder includes algs4.jar and adds it to the Java classpath.
    a. File → Project Structure (Ctrl + Alt + Shift + S) -> Libraries section: 
        If not: click + → Java → select the algs4.jar file.


- If you prefer to use some other shell (such as Powershell or zsh) or IDE (such as Eclipse or Netbeans), that’s fine—just be sure that you can configure it accordingly.

1. Add an import statement like the following at the top of your program:
```java
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
```
If you use IntelliJ and the provided project folder, IntelliJ will automatically add and remove import statements as needed, so you won’t need to type them.


```bash
~/Desktop/hello> javac-algs4 RandomWord.java

~/Desktop/hello> java-algs4 RandomWord
heads tails
tails

~/Desktop/hello> java-algs4 RandomWord
heads tails
heads

~/Desktop/hello> more animals8.txt
ant bear cat dog
emu fox goat horse

~/Desktop/hello> java-algs4 RandomWord < animals8.txt
emu

~/Desktop/hello> java-algs4 RandomWord < animals8.txt
bear
```

# javac 默认使用系统编码
Error: RandomWord.java:14: 错误: 编码 GBK 的不可映射字符 (0xA8)
表示你的 Java 源文件中包含了一些 GBK 编码无法识别的字符，而 javac 默认使用系统编码（Windows 下通常是 GBK）来读取源文件。

## IDE 设置（如 IntelliJ IDEA、Eclipse）
如果你是在 IDE 中开发项目，可以在 IDE 里设置默认编码为 UTF-8：
IntelliJ IDEA
打开 File > Settings (Windows) 或 IntelliJ IDEA > Preferences (macOS)
搜索 File Encodings
设置：
   - Global Encoding: UTF-8
   - Project Encoding: UTF-8
   - Default encoding for properties files: UTF-8
  
## 设置环境变量 JAVA_TOOL_OPTIONS
```cmd
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
```

```bash
export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
```