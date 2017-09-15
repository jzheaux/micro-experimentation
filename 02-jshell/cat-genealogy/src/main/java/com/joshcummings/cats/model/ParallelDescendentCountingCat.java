package com.joshcummings.cats.model;

public class ParallelDescendentCountingCat extends AbstractCat {
    public ParallelDescendentCountingCat() {}
    
    public long countDescendents() {
        return 1 + children.stream().parallel().mapToLong(Cat::countDescendents).sum();
    }
}
