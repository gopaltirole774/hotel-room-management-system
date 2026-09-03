package com.org.hotelroommanagementsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hotel Room Management System API",
                description = "CRUD APIs for managing hotel rooms",
                contact = @Contact(
                        name = "Gopal Tirole",
                        email = "gopaltirole774@gmail.com"
                )
        )
)
public class OpenApiConfig {

}