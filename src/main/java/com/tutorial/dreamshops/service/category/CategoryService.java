package com.tutorial.dreamshops.service.category;

import com.tutorial.dreamshops.exception.AlreadyExistsException;
import com.tutorial.dreamshops.exception.ResourceNotFoundException;
import com.tutorial.dreamshops.model.Category;
import com.tutorial.dreamshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found!")
                );
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found!");
        }
        return category;
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(
                categoryRepository::delete,
                () -> { throw new ResourceNotFoundException("Category not found!"); }
        );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
