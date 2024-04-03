package com.dh.Xplorando.repository;

import com.dh.Xplorando.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByNombreProducto(String nombreProducto);

    Producto findByUbicacion(String ubicacion);

  // Optional<Producto> findById(Long productoId);


    //Optional<List<Producto>> findProductoXFechas(LocalDate fechaInicio, LocalDate fechaFinal, Long id);

}
