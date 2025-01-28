package com.salesianostriana.dam.satapp.repository;

import com.salesianostriana.dam.satapp.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
}
