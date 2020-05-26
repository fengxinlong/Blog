package com.feng.dao;

import com.feng.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
	Type findByName(String name);

	/*
	* 通过分页获取大小
	*
	* */
	@Query("select t from Type t")
	List<Type> findTop(Pageable pageable);


}
