package com.example.client;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import com.example.client.entities.Client;
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT,properties={"spring.datasource.url=jdbc:h2:mem:testclient;DB_CLOSE_DELAY=-1","spring.datasource.driver-class-name=org.h2.Driver","spring.datasource.username=sa","spring.datasource.password=","spring.cloud.discovery.enabled=false","spring.cloud.consul.enabled=false","eureka.client.enabled=false"})
class ClientHttpTest {
    @Autowired TestRestTemplate http;
    @Test void listContainsSeededClients() { assertTrue(http.getForObject("/api/client",String.class).contains("Rabab")); }
    @Test void createReadUpdateDelete() {
        var created=http.postForEntity("/api/client",new Client(null,"Salma",22f),Client.class);
        assertEquals(HttpStatus.CREATED,created.getStatusCode());Long id=created.getBody().getId();
        assertEquals("Salma",http.getForObject("/api/client/"+id,Client.class).getNom());
        http.put("/api/client/"+id,new Client(null,"Salma modifiée",23f));
        assertEquals("Salma modifiée",http.getForObject("/api/client/"+id,Client.class).getNom());
        http.delete("/api/client/"+id);assertEquals(HttpStatus.NOT_FOUND,http.getForEntity("/api/client/"+id,String.class).getStatusCode());
    }
    @Test void invalidClientIsBadRequest() { assertEquals(HttpStatus.BAD_REQUEST,http.postForEntity("/api/client",new Client(null,"",-1f),String.class).getStatusCode()); }
}
