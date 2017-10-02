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
Cat fluffkins;
List<Cat> findDescendents(Cat ancestor) {
  List<Cat> descendents = new ArrayList<>();
  for ( Cat child : ancestor.getChildren() ) {
    descendents.add(child);
    descendents.addAll(findDescendents(child));
  }
  return descendents;
}
public class Cat {
  private String name;
  private List<Cat> children;

  public Cat(String name) {
    this.name = name;
    this.children = new ArrayList<>();
  }

  public Cat(String name, List<Cat> children) {
    this.name = name;
    this.children = children;
  }

  public List<Cat> getChildren() {
    return children;
  }
}
fluffkins = new Cat("Fluffkins");
Cat clarence = new Cat("Clarence");
Cat dora = new Cat("Dora");
Cat mom = new Cat("Mom", Arrays.asList(fluffkins, clarence, dora));
Cat bro = new Cat("Bro");
Cat gma = new Cat("G-ma", Arrays.asList(mom, bro));
findDescendents(gma);
findDescendents(gma);
findDescendents(mom);
public class Cat {
  private String name;
  private List<Cat> children;

  public Cat(String name) {
    this.name = name;
    this.children = new ArrayList<>();
  }

  public Cat(String name, List<Cat> children) {
    this.name = name;
    this.children = children;
  }

  public List<Cat> getChildren() {
    return children;
  }

  public String toString() {
    return name;
  }
}
findDescendents(gma);
fluffkins = new Cat("Fluffkins");
Cat clarence = new Cat("Clarence");
Cat dora = new Cat("Dora");
Cat mom = new Cat("Mom", Arrays.asList(fluffkins, clarence, dora));
Cat bro = new Cat("Bro");
Cat gma = new Cat("G-ma", Arrays.asList(mom, bro));
findDescendents(gma);