package com.dh.Xplorando.repository;

import com.dh.Xplorando.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica,Long> {
    Caracteristica findByNombreCaracteristica(String nombreCaracteristica);


}
