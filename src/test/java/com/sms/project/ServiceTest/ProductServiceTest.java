package com.sms.project.ServiceTest;

import com.sms.project.exceptions.ProductException;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.repo.PlanRepository;
import com.sms.project.product.dto.ProductDto;
import com.sms.project.product.entity.Product;
import com.sms.project.product.repo.ProductRepo;
import com.sms.project.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ModelMapper modelMapper;

    @Mock
    PlanRepository planRepository;

    Product product = new Product();
    ProductDto productDto = new ProductDto();
    Optional<Product> prod = Optional.of(new Product());
    List<PlanDetail> plan=new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        product.setId(1);
        product.setDeleted_at(null);
        product.setProductName("Jython");
        product.setDescription("From scratch");
        product.setPlanDetailList(plan);

        productDto = ProductDto.builder()
                .productName("Jython")
                .description("From Scratch")
                .build();
    }

    @Test
    void saveProduct() {
        Mockito.when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        Mockito.when(productRepo.save(product)).thenReturn(product);
        Assertions.assertThat(productService.createNewProduct(productDto)).isEqualTo("New product created");
    }

    @Test
    void getAllProducts() {
        Mockito.when(productRepo.findAll()).thenReturn(productList);
        Assertions.assertThat(productService.getAllProducts().size()).isEqualTo(0);
        Assertions.assertThat((productService.getAllProducts())).isNotNull();
    }

    @Test
    void deleteProductById() {
        Mockito.when(productRepo.findById(1)).thenReturn(prod);
        Assertions.assertThat(productService.deleteProductById(1)).isEqualTo("Product Deleted");
    }

//    @Test
//    void getProductById() throws ProductException {
//        Mockito.when(productRepo.findById(1)).thenReturn(prod);
//        Mockito.when(modelMapper.map(prod.get(),ProductDto.class)).thenReturn(productDto);
//        Assertions.assertThat(productService.getById(1)).isEqualTo(productDto.getId());
//    }

    @Test
    void updateProductById() {
        Mockito.when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        Mockito.when(productRepo.save(product)).thenReturn(product);
        Assertions.assertThat(productService.updateNewProduct(productDto)).isEqualTo("Values are updated");
    }

}
