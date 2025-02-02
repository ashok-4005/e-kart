package com.example.ecommerceBackend.service;

import com.example.ecommerceBackend.model.product;
import com.example.ecommerceBackend.repository.productRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class productService
{
    @Autowired
    private productRepo pr;
    public List<product> getProducts()
    {
        return pr.findAll();
    }

    public product getProductById(int id)
    {
        return pr.findById(id).orElse(new product(-1));
    }

    public product addOrUpdateProduct(product p1, MultipartFile image) throws IOException
    {
        p1.setImageName(image.getOriginalFilename());
        p1.setImageType(image.getContentType());
        p1.setImageData(image.getBytes());
        return pr.save(p1);
    }

    public void deleteProduct(int id)
    {
        pr.deleteById(id);
    }

    public List<product> searchProducts(String keyword)
    {
        return pr.searchProducts(keyword);
    }
}
