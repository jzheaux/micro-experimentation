To play with this plugin in your project, build it from the command line like so:

```
mvn clean install
```

And then add it to your project as a plugin dependency:

```
<plugin>
  <groupId>com.joshcummings</groupId>
  <artifactId>jshell-maven-plugin</artifactId>
  <version>1.0-SNAPSHOT</version>
</plugin>
```

Finally, build your own codebase and run jshell:run:

```
mvn clean install jshell:run
```

Note that this is just for demonstration. For it to work, you'll need to have a jar that is the same name as your artifactId (that is, without any further configuration on your part). Say, your artifactId is named cat-genealogy, then the build will need to generate a jar called cat-genealogy.jar.
