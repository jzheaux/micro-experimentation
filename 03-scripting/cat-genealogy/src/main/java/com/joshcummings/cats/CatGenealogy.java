package com.joshcummings.cats;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joshcummings.cats.model.Cat;
import com.joshcummings.cats.model.DescendentKnowingCat;
import com.joshcummings.cats.service.CatService;
import com.joshcummings.cats.service.SimpleCatService;

public class CatGenealogy {
    
    private CatService catService = new SimpleCatService(DescendentKnowingCat.class);
    
    public static void main(String[] args) {        
        CatGenealogy cg = new CatGenealogy();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        Integer port = 8080;
        
        try {
            port = Integer.parseInt(System.getProperty("port"));
        } catch ( Exception e ) {
            System.out.println("No port specified or found, using " + port);
        }
        
        port(port);
        
        get("/cat/list", (req, res) -> {
            if ( req.params("name") != null ) {
                return cg.catService.findCat(req.params("name"));
            }
            return cg.catService.listCats();
        }, gson::toJson);
        
        get("/cat/count", (req, res) -> {
            Map<String, Integer> result = new HashMap<>();
            result.put("count", cg.catService.listCats().size());
            return result;
        }, gson::toJson);
        
        post("/cat", (req, res) -> {
            Cat cat = gson.fromJson(req.body(), DescendentKnowingCat.class);
            cg.catService.addCat(cat);
            return cat;
        }, gson::toJson); 
        
        post("/mom/:momid/dad/:dadid/cat", (req, res) -> {
            Cat cat = gson.fromJson(req.body(), DescendentKnowingCat.class);
            
            try {
                Cat mom = cg.catService.findCat(Long.parseLong(req.params(":momid")));
                Cat dad = cg.catService.findCat(Long.parseLong(req.params(":dadid")));
                cat = cg.catService.addCat(cat.getName(), dad, mom);
            } catch ( Exception e ) {
                try {
                    Cat mom = cg.catService.findCat(req.params(":momid")).stream().findFirst().orElse(null);
                    Cat dad = cg.catService.findCat(req.params(":dadid")).stream().findFirst().orElse(null);
                    cat = cg.catService.addCat(cat.getName(), dad, mom);
                } catch ( Exception f ) { f.printStackTrace(); }
            }

            return cat;
        }, gson::toJson);
        
        get("/cat/:id", (req, res) -> {
            return cg.catService.findCat(Long.parseLong(req.params(":id")));
        }, gson::toJson);
        
        delete("/cat/:id", (req, res) -> {
            cg.catService.removeCat(Long.parseLong(req.params(":id")));
            res.status(204);
            return "";
        });
        
    }
}