package com.simple.ecommerce.WebConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




//customize the web configuration, such as adding CORS (Cross-Origin Resource Sharing) mappings.

@Configuration //beans
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
                .allowedOrigins("http://localhost:3000") // (it allows only requests from the frontend running at http://localhost:3000)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")// allow methods type to be requested from frontend
                .allowedHeaders("*") //allows all HTTP headers
                .allowCredentials(true); // Allow session cookies
    }
}
//.allowedHeaders("*") //allows all HTTP headers
//Accept: Specifies the media types that the client is willing to receive (e.g., Accept: application/json).
//
//Authorization: Contains credentials for authenticating the user (e.g., Authorization: Bearer <token>).
//
//Content-Type: Specifies the format of the data being sent in the body of the request (e.g., Content-Type: application/json).


//example:

//GET /api/products HTTP/1.1
//Host: example.com
//Accept: application/json
//Authorization: Bearer <JWT-token>


//HTTP/1.1 200 OK
//Content-Type: application/json
//Cache-Control: no-cache
//Set-Cookie: sessionId=abc123; Path=/; HttpOnly
//Content-Length: 345
//
//{
//    "products": [
//        {"id": 1, "name": "Product 1", "price": 20},
//        {"id": 2, "name": "Product 2", "price": 30}
//    ]
//}