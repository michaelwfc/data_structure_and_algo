-   [programming assignment specification](https://coursera.cs.princeton.edu/algs4/assignments/hello/specification.php)
-   [Assessment Guide](https://www.coursera.org/learn/algorithms-part1/resources/R2mre)

# Java programming environment on windows

[Java programming environment on windows](https://lift.cs.princeton.edu/java/windows/)

## 0. Install the Java Programming Environment

-   1. Open a Project in IntelliJ

-   2. Create a Program in IntelliJ

-   3. Compile and Execute the Program (from IntelliJ)

-   4. Compile and Execute the Program (from the command line)

-   5. Textbook Libraries (from the command line)

## FAQ

### What does the lift-java-installer.exe installer do?

In short, it installs and configures Java, IntelliJ, Git Bash, Xming, SpotBugs, PMD, Checkstyle, and our textbook libraries, along with accompanying command-line tools. Here is a more detailed list:

-   Installs Temurin OpenJDK 11.0.20 and adds it to the PATH.
-   Installs IntelliJ 2024.2 with customized user preferences.
-   Installs Git Bash 2.36.1 and adds it to the PATH.
-   Installs Xming 6.9.0.31.
-   Installs the following command-line tools for Java:
    -   The textbook libraries stdlib.jar and algs4.jar.
    -   Java wrapper scripts, including javac-algs4 and java-algs4.
        -   add scripts path to PATH (to compile and run from command line)
        -   add jar path to CLASSPATH( to compile and run from IntelliJ)
    -   SpotBugs 4.8.4; our SpotBugs configuration file spotbugs.xml; and wrapper script spotbugs.
    -   PMD 6.34.0; our PMD configuration file pmd.xml; and wrapper script pmd.
    -   Checkstyle 10.12.1; various configuration files (checkstyle-cos126.xml, checkstyle-cos226.xml, checkstyle-coursera.xml, and checkstyle-suppressions.xml); custom checks checkstyle-lift.jar; and wrapper script checkstyle.

### How does this custom version of IntelliJ different from the standard one?

IntelliJ is an industrial-strength integrated development environment (IDE), suitable for use by professional programmers. The installer configures your user preferences to make it more suitable for use by novice programmers:

-   Disables all built-in plugins except Terminal and JUnit. Installs the SpotBugs, Checkstyle-IDEA, Run-with-Arguments, Save-Actions, and Archive browser plugins.
-   Eliminates or reduces various popup elements (lightbulbs, code folding, breadcrumbs, gutter markers, notifications, parameter hints).
-   Simplifies menus and toolbars, hiding advanced options.
-   Disables live templates and postfix completion.
-   Adopts the Obsidian Black color scheme.
-   Auto-configures Java upon installation.
-   Adds a few keyboard shortcuts.

The course-specific project folders perform additional customizations:

-   Streamlines autocomplete to display only relevant libraries (such as java.lang, java.util, and algs4.jar).
-   Configures SpotBugs and Checkstyle with course-specific rules.
-   Provides course-specific libraries (such as algs4.jar).
-   Enables auto-formatting of code on save.
-   Enables auto-importing of Java libraries.

# Use algs4.jar

## Use the following library functions from algs4.jar

-   StdIn.readString(): reads and returns the next string from standard input.
-   StdIn.isEmpty(): returns true if there are no more strings available on standard input, and false - otherwise.
-   StdOut.println(): prints a string and terminating newline to standard output. It’s also fine to use System.out.println() instead.
-   StdRandom.bernoulli(p): returns true with probability p and false with probability 1−p

    In order to access these library functions, you must do the following two things:

## 1. Add algs4.jar to the Java classpath.

This typically requires a different mechanism from the command line and the IDE.

-   If you used our autoinstaller, the Bash commands javac-algs4 and java-algs4 add algs4.jar to the Java classpath.
-   If you use IntelliJ, the supplied IntelliJ project folder includes algs4.jar and adds it to the Java classpath.
    a. File → Project Structure (Ctrl + Alt + Shift + S) -> Libraries section:
    If not: click + → Java → select the algs4.jar file.
-   If you prefer to use some other shell (such as Powershell or zsh) or IDE (such as Eclipse or Netbeans), that’s fine—just be sure that you can configure it accordingly.

## 2. Add an import statement like the following at the top of your program:

```java
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
```

If you use IntelliJ and the provided project folder, IntelliJ will automatically add and remove import statements as needed, so you won’t need to type them.

## 3. add scripts to PATH

## compile and run on command line examples

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

-   Global Encoding: UTF-8
-   Project Encoding: UTF-8
-   Default encoding for properties files: UTF-8

## 设置环境变量 JAVA_TOOL_OPTIONS

```cmd
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
```

```bash
export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
```

# [Assessment Report](https://www.coursera.org/learn/algorithms-part1/resources/R2mre)

Here is some information to help you interpret the assessment report. See the [Assessment Guide](https://www.coursera.org/learn/algorithms-part1/resources/R2mre) for more details.

## Compilation:

we compile your .java files using a Java 8 compiler. Any error or warning messages are displayed and usually signify a major defect in your code. If your program does not compile, no further tests are performed.

## API:

we check that your code exactly matches the prescribed API (no extra methods and no missing methods). If it does not, no further tests are performed.

## Correctness:

we perform a battery of unit tests to check that your code meets the specifications.

## Memory:

we determine the amount of memory according to the 64-bit memory cost model from lecture.
The Memory test ensures that your program utilizes memory efficiently. As with Correctness tests, memory tests vary widely in operation and formatting. The autograder uses
[classmexer](http://www.javamex.com/classmexer) to measure the memory of an object. We execute with the -XX:-UseCompressedOops command-line option to ensure that the memory model is consistent with the 64-bit memory model from lecture.

## Timing:

we measure the running time and count the number of elementary operations.

This course is built around the notion of efficient algorithms. Thus, we expect that your programs should not only solve the problems at hand, but should also do so in an efficient manner. The timing tests typically measure temporal efficiency in two ways. First, it measures the raw time your program needs to complete a set of tasks. For many assignments, the timing test will also count the number of elementary operations that your program makes to solve a problem of a given size. As a example, consider the timing test for Percolation.java below.

## Bugs:

### SpotBugs

SpotBugs 是一个 Java 静态分析工具，用于检测代码中潜在的 bug 模式（如空指针异常、资源泄漏、不正确的并发访问等）。
它基于字节码进行分析，不需要运行程序即可发现潜在问题。

we run [SpotBugs](https://spotbugs.github.io/) to check for common bug patterns in Java programs. A warning message strongly suggests a bug in your code but occasionally there are false positives. Here is a summary of [bug descriptions](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html) , which you can use to help decode warning messages.

The autograder uses SpotBugs 4.0.3 with the configuration file [spotbugs.xml](https://lift.cs.princeton.edu/java/spotbugs.xml)

#### 方法三：使用 IntelliJ IDEA 内置支持

IntelliJ IDEA 社区版和 Ultimate 版都内置了对 SpotBugs 的支持：
安装插件：Settings > Plugins > Search "SpotBugs"。
Restart IntelliJ IDEA
右键点击你要分析的类或包 → Run SpotBugs on ...。
查看结果面板中的警告信息

##### 配置 IntelliJ 使用自定义的 spotbugs.xml

Use Maven or Gradle + SpotBugs plugin (recommended for custom rules)

1.  File > Settings > Tools > SpotBugs
2.  设置如下选项：

-   SpotBugs home directory：选择你本地的 SpotBugs 安装路径（如果你本地没有安装，插件会自动下载）。
-   Effort: 选择 Max 获取最全面的结果。
-   Threshold: 选择你希望看到的最低严重级别（推荐 Low）。
-   Include filter files: 添加你的 spotbugs.xml 文件路径。
    -   点击 + 号 → 浏览到你的 spotbugs.xml。
-   其他可选设置：
    -   是否检查测试代码
    -   输出报告格式（HTML/XML 等）

3. 确认保存设置。

### PMD

PMD scans Java source code and looks for common bug patterns. The autograder uses PMD 6.3.0 with the configuration file [pmd.xml](https://lift.cs.princeton.edu/java/pmd.xml) .
Here is a list of [bug descriptions](https://pmd.sourceforge.io/pmd-6.3.0/pmd_rules_java.html) .
Occasionally, PMD reports false positives, so the autograder does not count the results toward your score.

## Style:

we run [Checkstyle](https://checkstyle.sourceforge.io/) to automatically checks the style of your Java programs. Here is a list of available
[Checkstyle checks](https://checkstyle.sourceforge.io/checks.html) , which you can use to help decode any warning messages.

The autograder uses Checkstyle 8.31 with the configuration file [checkstyle-coursera.xml](https://lift.cs.princeton.edu/java/checkstyle-coursera.xml)

### 3. Using Checkstyle in an IDE (e.g., IntelliJ IDEA or Eclipse)

For IntelliJ IDEA:
Go to Settings > Tools > Checkstyle.
Click + and select your checkstyle.xml file.
Apply settings and enable the Checkstyle inspection.

4. Validate Code Against Custom Rules
   Once configured, Checkstyle will validate your code against the rules defined in your checkstyle.xml. Any violations will be reported during the build or within the IDE.
