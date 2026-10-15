package com.example.car.models;

import lombok.Builder;

/** Réponse enrichie, distincte de l'entité persistée dans carservicedb. */
@Builder
public record CarResponse(Long id,String brand,String model,String matricule,Long clientId,Client client) { }
