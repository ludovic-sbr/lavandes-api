package com.feliiks.gardons.config;

import com.feliiks.gardons.sqlmodels.LocationModel;
import com.feliiks.gardons.sqlmodels.ReservationModel;
import com.feliiks.gardons.sqlmodels.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(UserModel.class);
        config.exposeIdsFor(ReservationModel.class);
        config.exposeIdsFor(LocationModel.class);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}