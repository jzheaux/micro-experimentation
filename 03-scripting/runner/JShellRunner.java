import jdk.jshell.JShell;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class JShellRunner {
  @Parameter(description="args")
  List<String> programArgs;

  @Parameter(names="--startup", description="The startup script, if any")
  String startupScript;

  @Parameter(names="--class-path", description="The class-path for the remote agent, if any")
  String classpath;

  public static void main(String[] args) throws IOException {
    JShellRunner runner = new JShellRunner();
    JCommander commander = new JCommander(runner);
    commander.parse(args);
    commander.setProgramName("jshell-runner");
    
    List<String> programArgs = runner.programArgs;
    if ( !programArgs.isEmpty() ) {
      String program = programArgs.get(0);
      try ( JShell js = JShell.create() )  {
        js.onSnippetEvent(event -> System.out.println(event.value()));

        if ( runner.classpath != null ) {
          js.addToClasspath(runner.classpath);
        }

        for ( int i = 1; i < programArgs.size(); i++ ) {
          js.eval("\"" + programArgs.get(i) + "\"");
        }

        if ( runner.startupScript != null ) {
          List<String> lines = Files.readAllLines(Paths.get(runner.startupScript));
          for ( String line : lines ) {
            js.eval(line);
          }
        }

        List<String> lines = Files.readAllLines(Paths.get(program));
        for ( String line : lines ) {
          js.eval(line);
        }
      }
    } else {
      System.out.println("Nothing to do");
    }
  }
}
