package com.example.car.services;
import com.example.car.models.Client;
import org.springframework.stereotype.Service;
/** Communication synchrone resttemplate : résolution et transport restent hors du contrôleur. */
@Service
public class ClientApi { private final org.springframework.web.client.RestTemplate http;
        private final String base;
        public ClientApi(org.springframework.web.client.RestTemplate http,@org.springframework.beans.factory.annotation.Value("${client.base-url:http://localhost:8888/SERVICE-CLIENT}") String base) { this.http=http;this.base=base; }
        public Client findClientById(Long id) { return http.getForObject(base+"/api/client/"+id,Client.class); } }
