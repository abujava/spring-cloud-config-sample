package com.abujava.configserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
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

@Component
@ConfigurationProperties(prefix = "app")
public class ClientProperties {

    private List<Client> clients = new ArrayList<>();

    public ClientProperties() {
    }

    public ClientProperties(List<Client> clients) {
        this.clients = clients;
    }

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


    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
