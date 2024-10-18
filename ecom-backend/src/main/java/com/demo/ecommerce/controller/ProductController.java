package com.demo.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
		Product product = productService.findById(id);
		if (product.getId() > 0)
			return new ResponseEntity<>(product, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
		Product savedProduct;
		try {
			savedProduct = productService.addOrUpdateProduct(product, imageFile);
			return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

		} catch (IOException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/product/{id}/image")
	public ResponseEntity<byte[]> getImageById(@PathVariable("id") int id) {
		Product product = productService.findById(id);
		if (product.getId() > 0)
			return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable("id") int id,@RequestPart Product product,@RequestBody MultipartFile imageFile) {
		Product updatedProduct=null;
		try {
			updatedProduct =productService.addOrUpdateProduct(product, imageFile);
			return new ResponseEntity<>("product updated successfully",HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
		Product product=productService.findById(id);
		if(product.getId()>0) {
			productService.deleteProduct(id);
			return new ResponseEntity<>("product deleted successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
//		productService.resetPrimaryKey();
	}
	
	//SEARCH FEATURE.....................
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
		List<Product> products=productService.searchByKeyword(keyword);
//		System.out.println("searching for="+keyword);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
}
