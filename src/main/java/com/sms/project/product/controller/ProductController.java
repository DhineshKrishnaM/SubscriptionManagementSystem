package com.sms.project.product.controller;


import com.sms.project.exceptions.ProductException;
import com.sms.project.product.dto.ProductDto;
import com.sms.project.product.entity.Product;
import com.sms.project.product.repo.ProductRepo;
import com.sms.project.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/v1")
public class ProductController {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepo;

    @PreAuthorize("hasAnyRole('ROLE_WRITER', 'ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_WRITER')")
    @PostMapping("/create")
    public ResponseEntity<String> createNewProduct(@RequestBody ProductDto productdto) {
        productService.createNewProduct(productdto);
        return new ResponseEntity<>("Product created", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_WRITER', 'ROLE_ADMIN')")
    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteExistProduct(@RequestParam int id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>("Product has been deleted", HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<String> updateExistValue(@PathVariable("id") int id, @RequestBody ProductDto productdto) throws ProductException {
        ProductDto product = productService.getById(id);
        product.setProductName(productdto.getProductName());
        product.setDescription(productdto.getDescription());
        Product product1 = modelMapper.map(product, Product.class);
        productRepo.save(product1);
        return new ResponseEntity<>("Updated successfully..", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_B2B', 'ROLE_B2C')")
    @GetMapping("/getById/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") int productId) throws ProductException {
        ProductDto product = productService.getById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
