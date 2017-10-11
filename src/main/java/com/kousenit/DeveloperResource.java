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

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Developer getDeveloper(@PathVariable Integer id) {
        logger.info("Returning developer with id=" + id);
        return developers.get(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Collection<Developer> getAllDevelopers() {
        logger.info("Returning all developers");
        return developers.values();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Developer addDeveloper(@RequestBody String name) {
        int id = counter.incrementAndGet();
        Developer dev = new Developer(id, name);
        developers.put(id, dev);
        logger.info("Added developer with id=" + id + ", name=" + name);
        return dev;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Developer update(@PathVariable Integer id, @RequestBody String name) {
        Developer developer = new Developer(id, name);
        developers.put(id, developer);
        logger.info("Updated developer with id=" + id + ", name=" + name);
        return developer;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeveloper(@PathVariable Integer id) {
        logger.info("Removing developer with id=" + id);
        developers.remove(id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        logger.info("Removing all developers");
        developers.clear();
    }
}
