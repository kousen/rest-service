package com.kousenit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeveloperResourceTests {
    private List<String> names = Arrays.asList("Fred", "Barney", "Wilma", "Betty");
    private Map<Integer, Developer> developers = new HashMap<>();

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() {
        names.forEach(name -> {
            Developer dev = template.postForObject("/dev", name, Developer.class);
            developers.put(dev.getId(), dev);
        });
    }

    @After
    public void tearDown() {
        template.delete("/dev");
        developers.clear();
    }

    @Test
    public void addAndRemoveDeveloper() {
        Developer dev = template.postForObject("/dev", "Pebbles", Developer.class);
        assertEquals("Pebbles", dev.getName());
        template.delete("/dev/{id}", dev.getId());
    }

    @Test
    public void updateDeveloper() {
        Developer dev = template.postForObject("/dev", "Pebbles", Developer.class);
        template.put("/dev/{id}", "Bam-Bam", dev.getId());
        Developer newDev = template.getForObject("/dev/{id}", Developer.class, dev.getId());
        assertEquals("Bam-Bam", newDev.getName());
    }

    @Test
    public void getDeveloper() {
        developers.forEach((id, dev) -> {
//            Developer developer = template.getForObject("/dev/{id}", Developer.class, id);
            ResponseEntity<Developer> devEntity = template.getForEntity("/dev/{id}", Developer.class, id);
            assertEquals(HttpStatus.OK, devEntity.getStatusCode());
            Developer developer = devEntity.getBody();
            assertEquals(id.intValue(), developer.getId());
            assertEquals(dev.getName(), developer.getName());
        });
    }

    @Test
    public void getAllDevelopers() {
        Developer[] devs = template.getForObject("/dev", Developer[].class);
        assertEquals(names.size(), devs.length);
        Stream.of(devs).forEach(dev ->
                assertTrue(developers.containsKey(dev.getId())));
    }

}
