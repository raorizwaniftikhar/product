package com.java.spring.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.java.spring.domain.BrandProductRelation;
import com.java.spring.repository.BrandProductRelationRepository;
import com.java.spring.service.BrandProductRelationService;

@Service
public class BrandProductRelationServiceImpl implements BrandProductRelationService {
	private final Logger log = LoggerFactory.getLogger(BrandProductRelationServiceImpl.class);

	private final BrandProductRelationRepository brandProductRelationRepository;

	public BrandProductRelationServiceImpl(BrandProductRelationRepository brandProductRelationRepository) {
		this.brandProductRelationRepository = brandProductRelationRepository;
	}

	@Override
	public Optional<BrandProductRelation> findOne(String id) {
		log.info("Brands Service find {}", id);
		return brandProductRelationRepository.findById(id);
	}

	@Override
	public void delete(String id) {
		log.info("Brands Service delete {}", id);
		brandProductRelationRepository.deleteById(id);
	}

	@Override
	public BrandProductRelation save(BrandProductRelation brandProductRelation) {
		log.info("Brands Service save {}", brandProductRelation);
		return brandProductRelationRepository.save(brandProductRelation);
	}

}
