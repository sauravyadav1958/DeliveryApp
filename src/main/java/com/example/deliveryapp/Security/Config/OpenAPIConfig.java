package com.example.deliveryapp.Security.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Saurav",
            email = "Contact@saurav.com",
            url = "https://deliveryApp.com"
        ),
        title = "OPENAPI DOCUMENTATION",
        description = "Documentation for deliveryApp",
        version = "1.0",
        license = @License(
            name = "License name",
            url = "https://licenseUrl.com"
        ),
        termsOfService = "Terms of service"
    ),
    servers = {
        @Server(
            description = "LOCAL ENV",
            url = "http://localhost:8080"
        ),
        @Server(
            description = "DEV ENV",
            url = "http://delivery-app-1-0.onrender.com"
        )
    },
    security = {
        // token comes inside headers of the endPoints
        // Put this above a class if you want headers to be visible only for endpoints under that particular class, vice versa for specific endPoint/api.
        // Since we have defined it here, public api will also show Lock Icon in UI but would still run without authentication.
        @SecurityRequirement(
            name = "bearerAuth"
        )
    }
)
// Authentication feature
@SecurityScheme(
    name = "bearerAuth",
    description = "Jwt Auth description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {

}
