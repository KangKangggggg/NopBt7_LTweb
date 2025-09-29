package com.example.bt06.controller;

import com.example.bt06.entity.Category;
import com.example.bt06.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    // Trang chủ - chuyển hướng đến danh sách categories
    @GetMapping("/")
    public String home() {
        return "redirect:/admin/categories";
    }
    
    // Hiển thị danh sách categories (không phân trang)
    @GetMapping("")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/list";
    }
    
    // Hiển thị form thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("isEdit", false);
        return "admin/addOrEdit";
    }
    
    // Thêm mới category
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category, 
                             RedirectAttributes redirectAttributes) {
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Thêm category thành công!");
        return "redirect:/admin/categories";
    }
    
    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            model.addAttribute("isEdit", true);
            return "admin/addOrEdit";
        }
        return "redirect:/admin/categories";
    }
    
    // Cập nhật category
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute Category category, 
                               RedirectAttributes redirectAttributes) {
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Cập nhật category thành công!");
        return "redirect:/admin/categories";
    }
    
    // Xóa category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, 
                               RedirectAttributes redirectAttributes) {
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa category thành công!");
        return "redirect:/admin/categories";
    }
    
    // Tìm kiếm và phân trang
    @GetMapping("/search")
    public String searchCategories(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage;
        
        if (name != null && !name.trim().isEmpty()) {
            categoryPage = categoryService.findByNameContaining(name, pageable);
        } else {
            categoryPage = categoryService.findAll(pageable);
        }
        
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("name", name);
        return "admin/searchpaging";
    }
}