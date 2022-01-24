package com.example.carrentservice;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CarRentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentServiceApplication.class, args);
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map<String,String> config = new HashMap<String, String>();
        config.put("cloud_name", "ditxleyet");
        config.put("api_key", "724342485187749");
        config.put("api_secret", "GsMEY9FA17Z29nwaIKuhcLrfPPo");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }

}
