package com.example.car.services;
import com.example.car.entities.Car;
import com.example.car.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import com.example.car.models.CarResponse;
@Service
public class CarService {
    private final CarRepository cars;
    private final ClientApi clients;
    public CarService(CarRepository cars,ClientApi clients) { this.cars=cars;this.clients=clients; }
    public List<CarResponse> findAll() { return cars.findAll().stream().map(this::mapToCarResponse).toList(); }
    public CarResponse findById(Long id) { return mapToCarResponse(localById(id)); }
    public List<CarResponse> findByClient(Long id) { return cars.findByClientId(id).stream().map(this::mapToCarResponse).toList(); }
    public Car save(Car car) {
        if(car.getClientId()==null || car.getBrand()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Client et marque requis");
        clients.findClientById(car.getClientId());
        return enrich(cars.save(car));
    }
    public Car update(Long id,Car input) {
        var car=localById(id);
        if(input.getBrand()!=null) car.setBrand(input.getBrand());
        if(input.getModel()!=null) car.setModel(input.getModel());
        if(input.getMatricule()!=null) car.setMatricule(input.getMatricule());
        return enrich(cars.save(car));
    }
    public void delete(Long id) { cars.delete(localById(id)); }
    private Car localById(Long id) { return cars.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Voiture introuvable")); }
    private Car enrich(Car car) {
        if(car.getClientId()!=null) {
            try { car.setClient(clients.findClientById(car.getClientId())); }
            catch(Exception ex) { car.setClient(null); } // Le TP RestTemplate demande une réponse partielle si Client tombe.
        }
        return car;
    }
    private CarResponse mapToCarResponse(Car car) {
        enrich(car);
        return CarResponse.builder().id(car.getId()).brand(car.getBrand()).model(car.getModel())
            .matricule(car.getMatricule()).clientId(car.getClientId()).client(car.getClient()).build();
    }
}
