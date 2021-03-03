package com.moss.project.eneasy.dao;

import com.moss.project.eneasy.model.BaseModel;
import com.moss.project.eneasy.model.MafEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseJpaRepository<T extends MafEntity<ID>,ID extends Serializable > extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	Page<T> findAll(Specification<T> spec, Pageable pageable);
}
