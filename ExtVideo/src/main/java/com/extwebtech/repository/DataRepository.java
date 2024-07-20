package com.extwebtech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.extwebtech.bean.Data;

@Repository
public interface DataRepository extends JpaRepository<Data, Integer> {

}
