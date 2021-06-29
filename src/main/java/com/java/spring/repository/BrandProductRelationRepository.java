package com.java.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.domain.BrandProductRelation;

@Repository
public interface BrandProductRelationRepository extends MongoRepository<BrandProductRelation, String> {

}
