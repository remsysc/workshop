package com.sysc.workshop.product.service.category;

import com.sysc.workshop.core.exception.AlreadyExistsException;
import com.sysc.workshop.product.exception.CategoryNotFoundException;
import com.sysc.workshop.product.model.Category;
import com.sysc.workshop.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//create a category request
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).
                orElseThrow(()-> new CategoryNotFoundException("Category Not Found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name)).orElseThrow(()-> new CategoryNotFoundException("Category Not Found!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName())).
                map(categoryRepository::save).
                orElseThrow(()-> new AlreadyExistsException(category.getName()+" already exists!"));
    }

    @Override
    //find the old category by id, and replace the name if found
    // request is not needed since its only one data
    public Category updateCategory(Category newCategory, UUID existingCategory_id) {
        return Optional.ofNullable(getCategoryById(existingCategory_id)).map(existingCategory ->{
            existingCategory.setName(newCategory.getName());
            return categoryRepository.save(existingCategory);
        }).orElseThrow(()-> new CategoryNotFoundException("Category Not Found!"));
    }

    @Override
    public void deleteCategoryById(UUID id) {

        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
                ()-> {throw new CategoryNotFoundException("Category Not Found!");});

    }
}
