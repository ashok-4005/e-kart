package com.example.ecommerceBackend.controller;

import com.example.ecommerceBackend.model.product;
import com.example.ecommerceBackend.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class productController
{
    @Autowired
    private productService ps;
    @GetMapping("/products")
    public ResponseEntity<List<product>> getProducts()
    {
        return new ResponseEntity<>(ps.getProducts(),HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<product> getProductById(@PathVariable int id)
    {
        product p1 =ps.getProductById(id);
        if(p1.getId()>0)
            return new ResponseEntity<>(p1,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int id)
    {
        product p1 = ps.getProductById(id);
        if(p1.getId()>0)
            return new ResponseEntity<>(p1.getImageData(),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<product>> searchProducts(@RequestParam String keyword)
    {
        List<product> products= ps.searchProducts(keyword);
        System.out.println("With Keyword : "+keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart product product , @RequestPart MultipartFile imageFile)
    {
        product savedProduct = null;
        try
        {
            savedProduct = ps.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id , @RequestPart product product , @RequestPart MultipartFile imageFile)
    {
        product updatedProduct = null;
        try
        {
            updatedProduct = ps.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        product product = ps.getProductById(id);
        if(product!=null)
        {
            ps.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
