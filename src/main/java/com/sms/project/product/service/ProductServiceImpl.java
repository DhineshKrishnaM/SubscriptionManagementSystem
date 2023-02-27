package com.sms.project.product.service;

import com.sms.project.exceptions.ProductException;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.entity.PlanDto;
import com.sms.project.plandetails.repo.PlanRepository;
import com.sms.project.plandetails.service.PlanServiceImpl;
import com.sms.project.product.dto.ProductDto;
import com.sms.project.product.entity.Product;
import com.sms.project.product.repo.ProductRepo;
import com.sms.project.user.entity.User;
import com.sms.project.user.repo.UserRepo;
import com.sms.project.utility.errorcode.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    PlanServiceImpl planService;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("List of products are getting....");
        List<Product> prodList = productRepo.findAll();
        return prodList.stream().map(product->{
            List<PlanDetail> planList = getPlanList(product);
            ProductDto proDto = modelMapper.map(product, ProductDto.class);
            proDto.setPlanDto(planList.stream().map(planDetail -> modelMapper.map(planDetail, PlanDto.class)).collect(Collectors.toList()));
            return proDto;
        }).collect(Collectors.toList());
    }

    private List<PlanDetail> getPlanList(Product product) {
        return product.getPlanDetailList();
    }

    @Override
    public String createNewProduct(ProductDto product1dto) {
        log.info("Product created");
        Product prod = modelMapper.map(product1dto, Product.class);
        productRepo.save(prod);
        return "New product created";
    }

    @Override
    public String deleteProductById(int id) {
        log.info("Product have been deleting..");
        Product prod = productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException(ErrorCodes.PRODUCT_NOT_FOUND));
        prod.setDeleted_at(LocalDate.now());
        productRepo.saveAndFlush(prod);
        return "Product Deleted";
    }

    @Override
    public ProductDto getById(int id) throws ProductException {
        User userDetails=getLoginUser();
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductException("Product Not Available"));

        if(userDetails.getRole().toLowerCase().endsWith("b2b")){
            String str="b2b";
        List<PlanDetail> planList=getPlan(product,str);
        product.setPlanDetailList(planList);
        }

        if(userDetails.getRole().toLowerCase().endsWith("b2c")){
            String str="b2c";
            List<PlanDetail> planList=getPlan(product,str);
            product.setPlanDetailList(planList);
        }
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setPlanDto(product.getPlanDetailList().stream().map(planDetail -> modelMapper.map(planDetail,PlanDto.class)).collect(Collectors.toList()));
        return productDto;
    }

    private List<PlanDetail> getPlan(Product product,String str) {
        List<PlanDetail> plans = product.getPlanDetailList();
        return plans.stream().filter(planDetail ->
            planDetail.getPlanTypeFor().toLowerCase().endsWith(str)
        ).collect(Collectors.toList());
    }

    @Override
    public String updateNewProduct(ProductDto product) {
        Product prod = modelMapper.map(product, Product.class);
        productRepo.save(prod);
        log.info("Product has been updated..");
        return "Values are updated";
    }
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userRepo.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }
}
