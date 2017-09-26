import java.io.*;
import java.math.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.prefs.*;
import java.util.regex.*;
import java.util.stream.*;

/set mode math concise -quiet

/set feedback math

public class Cat {
  private String name;
}

/set prompt "c:\\> " "> "

/set prompt math "%s:> " "> "

public class Cat {
  private String name;
}

/set prompt math "= " " "

8 + 1

7 + 3

/set feedback

/set prompt math "\033[1;33m= \033[0m" " "

8 + 1
8 + 1

/set truncation concise

/set truncation math 10

400 + 1000
400 + 1000000000000000L

/set truncation math 0
/set truncation math 1000 expression,varvalue

String x = "123";
new BigDecimal("10000000000000000000000000000000000000000")
