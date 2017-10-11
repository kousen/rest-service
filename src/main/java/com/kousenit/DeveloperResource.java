package com.kousenit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@RestController
@RequestMapping("/dev")
public class DeveloperResource {
    private static final Logger logger = Logger.getLogger(DeveloperResource.class.getName());

    private AtomicInteger counter = new AtomicInteger();
    private Map<Integer, Developer> developers = new HashMap<>();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Developer getDeveloper(@PathVariable Integer id) {
        logger.info("Returning developer with id=" + id);
        return developers.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Developer> getAllDevelopers() {
        logger.info("Returning all developers");
        return developers.values();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Developer addDeveloper(@RequestBody Developer developer) {
        int id = counter.incrementAndGet();
        Developer dev = new Developer(id, developer.getName());
        developers.put(id, dev);
        logger.info("Added developer with id=" + id + ", name=" + developer.getName());
        return dev;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Developer update(@PathVariable Integer id, @RequestBody Developer developer) {
        Developer d = new Developer(id, developer.getName());
        developers.put(id, d);
        logger.info("Updated developer with id=" + id + ", name=" + developer.getName());
        return developer;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeveloper(@PathVariable Integer id) {
        logger.info("Removing developer with id=" + id);
        developers.remove(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        logger.info("Removing all developers");
        developers.clear();
    }
}
