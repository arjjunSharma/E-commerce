package com.ecommerce.product.service;

import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final  String uploadDir =System.getProperty("user.dir")+"/uploads/products/";

  @Override
  public ProductDto createProduct(ProductDto dto) {
    Category category = null;
    if (dto.getCategoryId() != null) {
      category = new Category();

      category.setId(dto.getCategoryId());
    }
    Product product = productMapper.toEntity(dto, category);
    Product saved = productRepository.save(product);
    return productMapper.toDto(saved);

  }

  @Override
  public ProductDto updateProduct(Long id, ProductDto dto) {
    Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setDiscountPrice(dto.getDiscountPrice());
    if (dto.getCategoryId() != null) {
      Category category = new Category();
      category.setId(dto.getCategoryId());
      product.setCategory(category);
    }
    Product updated = productRepository.save(product);
    return productMapper.toDto(updated);
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);

  }

  @Override
  public ProductDto getProductById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(" product is not found"));
    return productMapper.toDto(product);

  }

  @Override
   public ProductDto uploadImage(Long productId, MultipartFile file) throws IOException
  {
   Product product =  productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));

   if(file.isEmpty())throw new RuntimeException("Image file is empty");
   long maxSize = 4*1024*1024;
   if( file.getSize() > maxSize)throw new RuntimeException("File must be less than 4 MB");

   List<String> allowedType = List.of("image/jpeg","image/jpg","image/png");
   //here we are checking the  extension of the file to be uploading
   if(!allowedType.contains(file.getContentType()))
   {
    throw new RuntimeException("Only jpg , png  and  jpeg  are allowed! Thank You");
   }

   String originalName  = file.getOriginalFilename();

   if(originalName == null  || originalName.contains("."))  
   {
    throw new RuntimeException("Invalid File name");
   }

   String ext = originalName.substring(originalName.lastIndexOf(".")+1).toLowerCase();

   List<String> allowedExt = List.of("jpg" , "png","jpeg");

   if(!allowedExt.contains(ext))
   {
       throw new RuntimeException("Invalid image extension");

   }

   File folder =  new File(uploadDir);
   if(!folder.exists())
   {
       folder.mkdir();
   }
   String fileName = UUID.randomUUID().toString()+"."+ext;
      Path filePath =Paths.get(uploadDir+fileName);
      Files.write(filePath , file.getBytes());
      String  imageUrl = "/products/images"+fileName;
      product.setImageUrl(imageUrl);
     Product saved =productRepository.save(product);
   return productMapper.toDto(product);


  }

  @Override
  public Page<ProductDto> getAllProducts(int page, int size, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Product> productPage = productRepository.findAll(pageable);
    return productPage.map(productMapper::toDto);
  }

  @Override
  public Page<ProductDto> searchProducts(String keyword, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> productPage = productRepository.searchProducts(keyword, pageable);

    return productPage.map(productMapper::toDto);
  }

  @Override
  public Page<ProductDto> filterProducts(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page,
      int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> productPage = productRepository.advanceFilter(null, categoryId, minPrice, maxPrice, pageable);

    return productPage.map(productMapper::toDto);
  }

  @Override
  public Page<ProductDto> advanceFilter(String keyword, Long categoryId, Double minPrice, Double maxPrice, int page,
      int size, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Product> productPage = productRepository.advanceFilter(keyword, categoryId, minPrice, maxPrice, pageable);

    return productPage.map(productMapper::toDto);
  }
}
