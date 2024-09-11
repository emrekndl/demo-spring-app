package com.example.demospringapp.product.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demospringapp.IQuery;
import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.Product;
import com.example.demospringapp.product.model.ProductDTO;

@Service
public class GetProductsService implements IQuery<Void, List<ProductDTO>> {
    // @Autowired
    private final IProductRepository productRespository;
    
    private static final Logger logger = LoggerFactory.getLogger(GetProductsService.class);

    public GetProductsService(IProductRepository productRespository) {
        this.productRespository = productRespository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(Void input) {
        logger.info("Executing " + getClass());
        List<Product> products = productRespository.findAll();
        List<ProductDTO> productsDTO = products.stream().map(ProductDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(productsDTO);
    }
}
