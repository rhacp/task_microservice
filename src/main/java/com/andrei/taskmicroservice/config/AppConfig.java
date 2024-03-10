package com.andrei.taskmicroservice.config;

import com.andrei.taskmicroservice.models.dtos.InputDTO;
import com.andrei.taskmicroservice.models.entities.Input;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }

    @Bean(name = "propertyMapper")
    public TypeMap<InputDTO, Input> propertyMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<InputDTO, Input> propertyMapper = modelMapper.createTypeMap(InputDTO.class, Input.class);
        propertyMapper.addMapping(InputDTO::getOperation_number, Input::setOperationNumber);
        propertyMapper.addMapping(InputDTO::getInput_array, Input::setInputArray);

        return propertyMapper;
    }
}
