package com.sms.project.ControllerTest;

import com.sms.project.exceptions.ProductException;
import com.sms.project.product.controller.ProductController;
import com.sms.project.product.dto.ProductDto;
import com.sms.project.product.entity.Product;
import com.sms.project.product.repo.ProductRepo;
import com.sms.project.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    Product product = new Product();
    ProductDto productDto = new ProductDto();
    Optional<Product> prod = Optional.of(new Product());
    List<Product> productList = new ArrayList<>();
    List<ProductDto> productDtoList = new ArrayList<>();
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductController controller;
    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    void setup() {
        product.setId(1);
        product.setDeleted_at(null);
        product.setProductName("Basic python");
        product.setDescription("Every promo");

        productDto.setId(1);
        productDto.setProductName("Basic python");
        productDto.setDescription("Every promo");
    }
    @DisplayName("To create a new product")
    @Test
    void saveProduct() {
        Mockito.when(productService.createNewProduct(productDto)).thenReturn("Product created");
        Assertions.assertThat(controller.createNewProduct(productDto).getStatusCodeValue()).isEqualTo(201);
    }
    @DisplayName("List of products")
    @Test
    void listOfAllProduct() {
        Mockito.when(productService.getAllProducts()).thenReturn(productDtoList);
        Assertions.assertThat(controller.getAllProducts().getStatusCodeValue()).isEqualTo(200);
    }
    @DisplayName("Test case for delete particular product based on ID ")
    @Test
    void deleteProduct() {
        Mockito.when(productService.deleteProductById(1)).thenReturn("Product Deleted");
        Assertions.assertThat(controller.deleteExistProduct(1).getStatusCodeValue()).isEqualTo(200);
    }
    @DisplayName("To fetch the particular product based on Id")
    @Test
    void getProductById() throws ProductException {
        Mockito.when(productService.getById(1)).thenReturn(productDto);
        Assertions.assertThat(controller.getProduct(1).getStatusCodeValue()).isEqualTo(200);
    }
    @DisplayName("Test case for update a particular field in existing product")
    @Test
    void updateProduct() throws ProductException {
        Mockito.when(productService.getById(1)).thenReturn(productDto);
        Mockito.when(modelMapper.map(productDto,Product.class)).thenReturn(product);
        Mockito.when(productRepo.save(product)).thenReturn(product);
        Assertions.assertThat(controller.updateExistValue(1,productDto).getStatusCodeValue()).isEqualTo(200);
            }
}
