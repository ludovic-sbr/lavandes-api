package com.feliiks.gardons.config;

import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.entities.Location;
import com.feliiks.gardons.entities.Reservation;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Reservation.class);
        config.exposeIdsFor(Location.class);
    }
}