package com.abujava.authservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * This class is not documented :(
 *
 * @author Muhammad Muminov
 * @since 5/11/2023
 */
@Component
public class Startup implements CommandLineRunner {
    private final Environment environment;

    public Startup(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(String... args) throws Exception {
        String value = environment.getProperty("app.refresh-time");
        System.out.println(value);
    }
}
