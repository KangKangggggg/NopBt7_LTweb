package com.example.bt06.controller;

import com.example.bt06.entity.Product;
import com.example.bt06.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // Inject ProductService thông qua constructor
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Hiển thị danh sách sản phẩm với phân trang
     */
    @GetMapping
    public String listProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        // Pageable với sắp xếp theo id tăng dần
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        // Gọi service để lấy danh sách sản phẩm
        Page<Product> productPage = productService.listProducts(keyword, pageable);

        // Gửi dữ liệu sang view
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "product/list"; // templates/product/list.html
    }

    /**
     * Hiển thị form tạo mới sản phẩm
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/create"; // templates/product/create.html
    }

    /**
     * Lưu sản phẩm (cả tạo mới và chỉnh sửa)
     */
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              RedirectAttributes redirectAttributes) {
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("success", "Lưu sản phẩm thành công!");
        return "redirect:/products";
    }

    /**
     * Hiển thị form chỉnh sửa sản phẩm
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Product product = productService.getProduct(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm!");
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product/edit"; // templates/product/edit.html
    }

    /**
     * Xem chi tiết sản phẩm
     */
    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable("id") Long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        Product product = productService.getProduct(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm!");
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product/view"; // templates/product/view.html
    }

    /**
     * Xóa sản phẩm
     */
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id,
                                RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công!");
        return "redirect:/products";
    }
}
