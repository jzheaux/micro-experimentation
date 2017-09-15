package com.joshcummings.cats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.joshcummings.cats.model.Cat;
import com.joshcummings.cats.model.DescendentCountingCat;
import com.joshcummings.cats.model.DescendentKnowingCat;
import com.joshcummings.cats.service.CatService;
import com.joshcummings.cats.service.SimpleCatService;

@State(Scope.Benchmark)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class DescendentsBenchmark {
    private CatService knowing      = new SimpleCatService(DescendentKnowingCat.class);
    private CatService counting     = new SimpleCatService(DescendentCountingCat.class);

    private long       baseline     = 350;
    private long       numberOfCats = 128;

    public DescendentsBenchmark() {
        System.out.println("inited: " + Thread.currentThread().getName());
        init(counting);
    }

    public void init(CatService... services) {
        Random rnd = new Random();
        for (int i = 0; i < numberOfCats / 2; i++) {
            for (CatService service : services) {
                service.addCat("Cat" + i, null, null);
            }
        }

        for (int i = 0; i < numberOfCats / 2; i++) {
            for (CatService service : services) {
                List<Cat> cats = new ArrayList<>(service.listCats());
                Cat dad = cats.get(rnd.nextInt(cats.size()));
                Cat mom = cats.get(rnd.nextInt(cats.size()));
                service.addCat("Cat" + (i + numberOfCats / 2), dad, mom);
            }
        }
    }

    @Benchmark
    public long baseline() {
        return baseline;
    }

    @Benchmark
    public long listCats() {
        return counting.listCats().stream().mapToLong(c -> 10).sum();
    }

    @Benchmark
    public long countCounting() {
        return counting.listCats().stream().mapToLong(Cat::countDescendents).sum();
    }
}
