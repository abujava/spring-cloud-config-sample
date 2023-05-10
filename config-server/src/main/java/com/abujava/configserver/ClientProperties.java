package com.abujava.configserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is not documented :(
 *
 * @author Muhammad Muminov
 * @since 5/10/2023
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class ClientProperties {

    private List<Client> clients = new ArrayList<>();

    public Set<String> toPathSet() {
        return clients.stream().map(Client::getName).collect(Collectors.toSet());
    }

    public String[] toPathArray() {
        String[] pathArray = new String[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            pathArray[i] = clients.get(i).getName();
        }
        return pathArray;
    }

    @Data
    public static class Client {
        private String name;
        private String path;
        private String username;
        private String password;

        public String getPathWithStartsWith() {
            return path + "/**";
        }

        public String getRole() {
            return String.format("hasRole('%S')", name);
        }
    }
}
