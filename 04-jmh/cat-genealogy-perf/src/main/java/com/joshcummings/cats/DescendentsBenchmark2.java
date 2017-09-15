package com.joshcummings.cats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import com.joshcummings.cats.service.CatService;
import com.joshcummings.cats.service.SimpleCatService;
import com.joshcummings.cats.model.Cat;
import com.joshcummings.cats.model.DescendentKnowingCat;
import com.joshcummings.cats.model.DescendentCountingCat;
import com.joshcummings.cats.model.ParallelDescendentCountingCat;

@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class DescendentsBenchmark2 {
    public enum CatServicePicker {
        KNOWING(DescendentKnowingCat.class), COUNTING(DescendentCountingCat.class),
        PARALLEL(ParallelDescendentCountingCat.class);
        
        Class<? extends Cat> catType;
        
        CatServicePicker(Class<? extends Cat> catType) { this.catType = catType; }
        
        CatService getService() {
            return new SimpleCatService(catType);
        }
    }
  
    @State(Scope.Benchmark)
    public static class BaselineState {
        Integer baseline = 350;
    }
    
    @State(Scope.Benchmark)
    public static class CatServiceState {
        CatService service;
        
        @Param({ "KNOWING", "COUNTING", "PARALLEL" }) CatServicePicker catType;
        
        @Param({ "128", "256", "512", "1024", "2048", "4096", "8192" }) Integer numberOfCats;
        
        @Setup(Level.Trial)
        public void init() {
            service = catType.getService();
            init(service, numberOfCats);
        }
        
        public void init(CatService service, int numberOfCats) {
            String fileName = "cat-tree-size-" + numberOfCats;
            try ( InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is)); ) {
                reader.lines()
                    .map(line -> Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray())
                    .forEach(split -> {
                        String catName = "Cat" + split[0];
                        
                        Cat dad = split[1] == -1 ? null : service.findCat("Cat" + split[1]).iterator().next();
                        Cat mom = split[2] == -1 ? null : service.findCat("Cat" + split[2]).iterator().next();
                        
                        service.addCat(catName, dad, mom);    
                    });
            } catch ( IOException e ) {
                throw new IllegalStateException(e);
            }
        }    
    }
    
    
    @Benchmark
    public long baseline(BaselineState state) {
        return state.baseline;
    }
    
    @Benchmark
    @Threads(3)
    public long listCats(CatServiceState state) {
        return state.service.listCats().stream().mapToLong(c -> 10).sum();
    }
       
    private static final String readWrite = "readWrite";
    
    @Benchmark
    @Group(readWrite)
    @GroupThreads(3)
    public long countCounting(CatServiceState state) {
        return state.service.listCats().stream().mapToLong(Cat::countDescendents).sum();
    }
    
    private static final Random rnd = new Random();
    
    @Benchmark
    @Threads(1)
    public void baselineAddRemove(CatServiceState state, Blackhole bh) {
        List<Cat> cats = new ArrayList<>(state.service.listCats());
        Cat dad = cats.get(rnd.nextInt(cats.size()));
        Cat mom = cats.get(rnd.nextInt(cats.size()));
        bh.consume(cats);
        bh.consume(dad);
        bh.consume(mom);
    }
    
    @Benchmark
    @Group(readWrite)
    @GroupThreads(1)
    public void addRemove(CatServiceState state, Blackhole bh) {
        List<Cat> cats = new ArrayList<>(state.service.listCats());
        Cat dad = cats.get(rnd.nextInt(cats.size()));
        Cat mom = cats.get(rnd.nextInt(cats.size()));
        bh.consume(cats);
        bh.consume(dad);
        bh.consume(mom);
        
        Cat cat = state.service.addCat("bob", dad, mom);
        Cat removed = state.service.removeCat(cat.getId());
        
        bh.consume(cat);
        bh.consume(removed);
    }
}