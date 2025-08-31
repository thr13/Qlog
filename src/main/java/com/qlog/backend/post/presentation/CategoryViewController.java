package com.qlog.backend.post.presentation;

import com.qlog.backend.post.application.CategoryService;
import com.qlog.backend.post.presentation.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryViewController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 페이지
     * @param model categories
     * @return resources/templates/category/list.html
     */
    @GetMapping
    public String categoryList(Model model) {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/list";
    }

}
