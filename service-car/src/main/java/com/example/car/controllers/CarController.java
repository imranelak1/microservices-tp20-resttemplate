package com.example.car.controllers;
import com.example.car.entities.Car;
import com.example.car.services.*;
import com.example.car.models.Client;
import com.example.car.models.CarResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController @RequestMapping("/api/car")
public class CarController {
    private final CarService service;
    public CarController(CarService service) { this.service=service; }
    @GetMapping public List<CarResponse> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public CarResponse findById(@PathVariable Long id) { return service.findById(id); }
    @GetMapping({"/client/{clientId}","/byClient/{clientId}"}) public List<CarResponse> findByClient(@PathVariable Long clientId) { return service.findByClient(clientId); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Car save(@RequestBody Car input) { input.setId(null);return service.save(input); }
    @PostMapping("/{clientId}") public Car saveForClient(@PathVariable Long clientId,@RequestBody Car input) { input.setId(null);input.setClientId(clientId);return service.save(input); }
    @PutMapping("/{id}") public Car update(@PathVariable Long id,@RequestBody Car input) { return service.update(id,input); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
