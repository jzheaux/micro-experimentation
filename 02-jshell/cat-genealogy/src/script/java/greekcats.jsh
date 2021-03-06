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