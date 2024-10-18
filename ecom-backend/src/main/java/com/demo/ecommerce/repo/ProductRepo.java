package com.demo.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.demo.ecommerce.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	@Query(value = "ALTER SEQUENCE communities_sno_seq RESTART WITH 1", nativeQuery = true)
	 void resetAutoIncrement();

	@Query("SELECT p FROM Product p WHERE " +
		       "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
		List<Product> searchByKeyword(String keyword);
}
