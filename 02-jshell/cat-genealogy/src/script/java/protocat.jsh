Cat fluffkins;

List<Cat> findDescendents(Cat ancestor) {
  List<Cat> descendents = new ArrayList<>();
  for ( Cat child : ancestor.getChildren() ) {
    descendents.add(child);
    descendents.addAll(findDescendents(child));
  }
  return descendnts;
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

Cat mom = new Cat(Arrays.asList(fluffkins, clarence, dora));

Cat bro = new Cat("Bro");

Cat gma = new Cat("G-ma", Arrays.asList(mom, bro));

findDescendents(gma);
