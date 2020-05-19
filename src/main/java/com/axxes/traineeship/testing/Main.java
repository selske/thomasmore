package com.axxes.traineeship.testing;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main {

    public static void main(final String[] args) {
        new SpringApplicationBuilder()
                .sources(Main.class)
                .run(args);
    }

}
