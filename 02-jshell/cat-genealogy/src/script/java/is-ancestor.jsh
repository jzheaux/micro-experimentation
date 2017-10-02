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
import com.joshcummings.cats.*;
import com.joshcummings.cats.model.*;
import com.joshcummings.cats.service.*;
Cat kronus = new DescendentCountingCat("Kronus", null, null);
Cat rhea = new DescendentCountingCat("Rhea", null, null);
Cat zeus = new DescendentCountingCat("Zeus", kronus, rhea);
Cat hera = new DescendentCountingCat("Hera", null, null);
Cat athena = new DescendentCountingCat("Athena", zeus, hera);

boolean isAncestor(Cat cat, Cat possibleAncestor) {
  return isAncestor(cat.getMom(), possibleAncestor) || isAncestor(cat.getDad(), possibleAncestor);
}

boolean isAncestor(Cat cat, Cat possibleAncestor) {
  if ( cat == null ) {
    return false;
  }

  if ( cat.getId() == possibleAncestor.getId() ) {
    return true;
  }

  return isAncestor(cat.getMom(), possibleAncestor) || isAncestor(cat.getDad(), possibleAncestor);
}

isAncestor(athena, kronus)

isAncestor(rhea, kronus)

boolean isAncestorHelper(Cat cat, Cat possibleAncestor, Set<Cat> visited) {
  if ( cat == null || !cat.add(visited) ) {
    return false;
  }

  if ( cat.getId() == possibleAncestor.getId() ) {
    return true;
  }

  return isAncestorHelper(cat.getMom(), possibleAncestor, visited) ||
    isAncestorHelper(cat.getDad(), possibleAncestor, visited);
}

boolean isAncestor(Cat cat, Cat possibleAncestor) {
  return isAncestorHelper(cat, possibleAncestor, new HashSet<>());
}

isAncestor(athena, kronus);

isAncestor(rhea, kronus);
