package com.flowerstore.flower_shop.controller;

import com.flowerstore.flower_shop.model.Product;
import com.flowerstore.flower_shop.service.IProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

//@RestController
//@RequestMapping("/product")
//@CrossOrigin(origins = "http://localhost:3000")
//public class ProductController {
//    private final MockDataService mockDataService;
//
//    public ProductController(MockDataService mockDataService) {
//        this.mockDataService = mockDataService;
//    }
//
//    @GetMapping
//    public List<Product> getAllProducts() {
//        return mockDataService.getProducts();
//    }
//
//    @PostMapping
//    public Product addProduct(@RequestBody Product product) {
//        return mockDataService.addProduct(product);
//    }
//
//    @GetMapping("/{id}")
//    public Product getProductById(@PathVariable Long id) {
//        return mockDataService.getProducts().stream()
//                .filter(product -> product.getId().equals(id))
//                .findFirst().orElse(null);
//    }
//    @PutMapping("/{id}")
//    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
//        return mockDataService.updateProduct(id, updatedProduct);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteProduct(@PathVariable Long id) {
//        mockDataService.deleteProduct(id);
//    }
//}


@Controller
@RequestMapping("/product")
public class ProductController {
    private final IProductService iProductService;

    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @GetMapping()
    public String showProducts(Model model) {
        List<Product> products = iProductService.getAllProducts();
        model.addAttribute("products", products);
        return "products/list-products";
    }

    @PostMapping("/save")
    public String saveProducts(@ModelAttribute Product product) {
        iProductService.addProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Product product = iProductService.getProductById(id);
            model.addAttribute("product", product);
            return "products/edit-product";
        } catch (NoSuchElementException e) {
            return "redirect:/product";
        }
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product) {
        iProductService.updateProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        iProductService.deleteProduct(id);
        return "redirect:/product";
    }
}