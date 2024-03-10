package com.andrei.taskmicroservice.repositories;

import com.andrei.taskmicroservice.models.entities.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRepository extends JpaRepository<Input, Long> {

    Input findInputById(Long Id);
}
