package com.demo.ecommerce.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	
	public List<Product> getAllProducts() {
		return  productRepo.findAll();
	}

	public Product findById(int id) {
		return productRepo.findById(id).orElse(new Product(-1));
	}
	
	
	public Product addOrUpdateProduct(Product product, MultipartFile imageFile) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return productRepo.save(product);
	}

	public void deleteProduct(int id) {
		productRepo.deleteById(id);
	}
	
	public void resetPrimaryKey() {
		productRepo.resetAutoIncrement();
	}

	public List<Product> searchByKeyword(String keyword) {
		return productRepo.searchByKeyword(keyword);
	}

}
