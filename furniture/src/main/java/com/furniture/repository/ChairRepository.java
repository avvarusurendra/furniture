package com.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.furniture.entity.Chair;
@Repository
public interface ChairRepository extends JpaRepository<Chair, Integer>{

}
