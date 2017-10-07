import jdk.jshell.JShell;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import jdk.jshell.SnippetEvent;
import jdk.jshell.SourceCodeAnalysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class JShellRunner {
  @Parameter(description="args")
  List<String> programArgs = new ArrayList<>();

  @Parameter(names="--startup", description="The startup script, if any")
  String startupScript;

  @Parameter(names="--class-path", description="The class-path for the remote agent, if any")
  String classpath;

  @Parameter(names="--add-module", description="The modules for the remote agent, if any")
  List<String> modules = new ArrayList<>();

  private static void parseFile(JShell js, String file) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get(file));
    SourceCodeAnalysis analysis = js.sourceCodeAnalysis();
    Iterator<String> it = lines.iterator();
    while ( it.hasNext() ) {
      String snippet = it.next();
      if ( snippet.startsWith("/") ) {
          if ( snippet.startsWith("/open") ) {
              parseFile(js, snippet.split(" ")[1]);
          } else if ( snippet.startsWith("/env --class-path") ) {
              js.addToClasspath(snippet.split(" ")[2]);
          }
      } else {
          SourceCodeAnalysis.CompletionInfo info = analysis.analyzeCompletion(snippet);
          SourceCodeAnalysis.Completeness completeness = info.completeness();
          while (completeness != SourceCodeAnalysis.Completeness.COMPLETE &&
                  completeness != SourceCodeAnalysis.Completeness.COMPLETE_WITH_SEMI &&
                  completeness != SourceCodeAnalysis.Completeness.EMPTY) {
              snippet += "\n" + it.next();
              info = analysis.analyzeCompletion(snippet);
              completeness = info.completeness();
          }

          js.eval(snippet);
      }
    }
  }

  public static void main(String[] args) throws IOException {
    JShellRunner runner = new JShellRunner();
    JCommander commander = new JCommander(runner);
    commander.parse(args);
    commander.setProgramName("jshell-runner");
    
    List<String> programArgs = runner.programArgs;
    if ( !programArgs.isEmpty() ) {
      String program = programArgs.get(0);

      JShell.Builder builder = JShell.builder()
              .remoteVMOptions("--add-modules=jdk.incubator.httpclient")
              .compilerOptions("--add-modules=jdk.incubator.httpclient");

      try ( JShell js = builder.build() )  {
/*        js.onSnippetEvent(event -> {
            if ( event.value() != null ) {
                System.out.println(event.value());
            }
        });
*/
        if ( runner.classpath != null ) {
          js.addToClasspath(runner.classpath);
        }

        for ( int i = 1; i < programArgs.size(); i++ ) {
          js.eval("\"" + programArgs.get(i) + "\"");
        }

        if ( runner.startupScript != null ) {
          parseFile(js, runner.startupScript);
        }

        parseFile(js, program);
      }
    } else {
      System.out.println("Nothing to do");
    }
  }
}
