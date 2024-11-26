package com.mydata.controller;

import java.nio.file.Files;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mydata.model.Product;
import com.mydata.model.ProductSave;
import com.mydata.repository.ProductRepository;

import jakarta.validation.Valid;



@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository repo; 
	
	@GetMapping("/index")
	public String getIndex() {
		return "index";
	}
	
	// Method Is for to get product list
	@GetMapping("/showproductlist")
	public String showProductList(@RequestParam(value = "category", required = false) String category, Model model) {
	    List<Product> products;
	    if (category != null && !category.isEmpty()) {
	        products = repo.findByCategory(category); 
	    } else {
	        products = repo.findAll(); 
	    }
	    
//	    List<Product> pro = repo.findAll();
//	    for (Product product : pro) {
//	        System.out.println("Product ID: " + product.getId() + ", Image File Name: " + product.getImageFileName());
//	    }
	    
	    model.addAttribute("products", products);
	    model.addAttribute("selectedCategory", category); // To indicate the active category on the page
	    return "Product";
	}

	
	
	
	// method to save products
	@GetMapping("/productsave")
	public String showCreatePage(Model model) {
		ProductSave productsave = new ProductSave();
		model.addAttribute("productsave", productsave);
		return "productsave";
	}
	
	@PostMapping("/create")
	public String createProduct(@Valid @ModelAttribute ProductSave productsave, BindingResult result) {
		
		MultipartFile image = productsave.getImageFile();
		Date createdAt = new Date();
		String storageFileName = createdAt.getTime() + "_" +image.getOriginalFilename();
		try {
			String uploadDir = "public/image/";
			Path uploadPath = Paths.get(uploadDir);
			
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			try (InputStream inputstream = image.getInputStream()){
				Files.copy(inputstream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}
		}catch(Exception ex) {
			System.out.println("Exception: "+ex.getMessage());
		}
		
		Product product = new Product();
        product.setName(productsave.getName());
        product.setBrand(productsave.getBrand());
        product.setCategory(productsave.getCategory());
        product.setPrice(productsave.getPrice());
        product.setDescription(productsave.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);
        //product.setImageFileName(productsave.getImageFile());
		
        repo.save(product);
        
		return "redirect:showproductlist";
	}
	
	
	// method to edit the product
	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam int id) {
	    try {
	        Product product = repo.findById(id).get();
	        model.addAttribute("product", product);
	        
	    } catch (Exception ex) {
	        System.out.println("Exception: " + ex.getMessage());
	    }
	    return "productedit";
	}
	
	@PostMapping("/edit")
	public String updateProduct(@RequestParam int id, @Valid @ModelAttribute("product") Product product, BindingResult result) {
	    if (result.hasErrors()) {
	        return "productedit"; 
	    }
	    try {
	        Product existingProduct = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
	        
	        existingProduct.setName(product.getName());
	        existingProduct.setBrand(product.getBrand());
	        existingProduct.setCategory(product.getCategory());
	        existingProduct.setPrice(product.getPrice());
	        existingProduct.setDescription(product.getDescription());
	        
	        repo.save(existingProduct); // Save updated product
	    } catch (Exception e) {
	        System.out.println("Exception: " + e);
	    }
	    return "redirect:showproductlist";
	}
		
	// this method for delete the product 
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam int id) {
		try {
			Product product = repo.findById(id).get();
			
			repo.delete(product);
			
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
		return "redirect:/showproductlist";
	}
}
