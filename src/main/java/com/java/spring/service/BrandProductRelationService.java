package com.java.spring.service;

import java.util.Optional;

import com.java.spring.domain.BrandProductRelation;

public interface BrandProductRelationService {

	BrandProductRelation save(BrandProductRelation brandProductRelation);

	Optional<BrandProductRelation> findOne(String id);

	void delete(String id);

}
