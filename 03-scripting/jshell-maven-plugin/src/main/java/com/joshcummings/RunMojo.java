package com.joshcummings;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import jdk.jshell.SnippetEvent;
import jdk.jshell.execution.JdiExecutionControlProvider;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;

import java.io.Console;
import java.util.HashMap;
import java.util.List;

/**
 * Goal which launches JShell using the project's classpath
 */
@Mojo( name = "run", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class RunMojo
    extends AbstractMojo
{
    @Parameter( defaultValue = "${project.artifactId}", property = "serviceName", required = false )
    private String serviceName;

    @Parameter( defaultValue = "${project.runtimeClasspathElements}" )
    private List<String> classpath;

    public void execute()
        throws MojoExecutionException
    {
        try {
            this.getLog().debug("Using " + classpath + " as the classpath for JShell session");

            Console console = System.console();

            try (JShell js =  JShell.builder()
                    .executionEngine(
                            new JdiExecutionControlProvider(),
                            new HashMap<>()).build()) {

                for ( String classpathElement : classpath ) {
                    js.addToClasspath(classpathElement);
                }

                do {
                    System.out.print(serviceName + "> ");
                    System.out.flush();
                    String input = console.readLine();
                    if (input == null) {
                        break;
                    }
                    List<SnippetEvent> events = js.eval(input);
                    for (SnippetEvent e : events) {
                        StringBuilder sb = new StringBuilder();
                        if (e.causeSnippet() == null) {
                            //  We have a snippet creation event
                            switch (e.status()) {
                                case VALID:
                                    sb.append("Successful ");
                                    break;
                                case RECOVERABLE_DEFINED:
                                    sb.append("With unresolved references ");
                                    break;
                                case RECOVERABLE_NOT_DEFINED:
                                    sb.append("Possibly reparable, failed  ");
                                    break;
                                case REJECTED:
                                    sb.append("Failed ");
                                    break;
                            }
                            if (e.previousStatus() == Snippet.Status.NONEXISTENT) {
                                sb.append("addition");
                            } else {
                                sb.append("modification");
                            }
                            sb.append(" of ");
                            sb.append(e.snippet().source());

                            System.out.println(sb);
                            if (e.exception() != null) {
                                System.out.println(e.exception().getMessage());
                            }
                            if (e.value() != null) {
                                System.out.printf("%s%n%n", e.value());
                            } else {
                                System.out.printf("%n");
                            }
                            System.out.flush();
                        }
                    }
                } while (true);
            }

        }/* catch (DependencyResolutionRequiredException e) {
            e.printStackTrace();
        } */finally {
            System.out.println("\nGoodbye");
        }
    }
}
