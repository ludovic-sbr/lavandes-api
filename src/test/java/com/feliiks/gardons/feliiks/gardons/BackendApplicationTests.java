package com.feliiks.gardons.feliiks.gardons;

import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private LocationRepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAllLocations() {
        List<LocationEntity> locations = repository.findAll();
        assertThat(locations).isNotEmpty();
    }

}
