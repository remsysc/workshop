package com.sysc.workshop.product.service.category;

import com.sysc.workshop.core.exception.AlreadyExistsException;
import com.sysc.workshop.product.dto.category.CategoryDto;
import com.sysc.workshop.product.dto.category.SearchCategoryRequestDTO;
import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import com.sysc.workshop.product.exception.CategoryNotFoundException;
import com.sysc.workshop.product.mapper.CategoryMapper;
import com.sysc.workshop.product.model.Category;
import com.sysc.workshop.product.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//create a category request
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository
            .findById(id)
            .orElseThrow(() ->
                new CategoryNotFoundException("Category Not Found!")
            );
    }

    @Override
    public Category getCategoryByName(String name) {
        return Optional.ofNullable(
            categoryRepository.findByName(name)
        ).orElseThrow(() ->
            new CategoryNotFoundException("Category Not Found!")
        );
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryMapper.toDtoList(
            categoryRepository.findAll()
        );
        return categoryDtos;
    }

    @Override
    public CategoryDto addCategory(String category) {
        if (categoryRepository.existsByName(category)) {
            throw new AlreadyExistsException(category + " already exists!");
        }

        return categoryMapper.toDto(
            categoryRepository.save(new Category(category))
        );
    }

    @Override
    //find the old category by id, and replace the name if found
    // request is not needed since its only one data
    public CategoryDto updateCategory(String newCategory, UUID id) {
        if (categoryRepository.existsByName(newCategory)) {
            throw new AlreadyExistsException(newCategory + " already exists!");
        }

        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresentOrElse(
            c -> {
                c.setName(newCategory);
                categoryRepository.save(c);
            },
            () -> {
                throw new CategoryNotFoundException("Category Not Found!");
            }
        );

        return categoryMapper.toDto(category.get());
    }

    @Override
    public void deleteCategoryById(UUID id) {
        categoryRepository
            .findById(id)
            .ifPresentOrElse(categoryRepository::delete, () -> {
                throw new CategoryNotFoundException("Category Not Found!");
            });
    }

    @Override
    public PagedResponseDTO<CategoryDto> searchCategories(
        SearchCategoryRequestDTO requestDTO
    ) {
        PageRequest pageable = PageRequest.of(
            requestDTO.getPage(),
            requestDTO.getSize()
        );
        Page<Category> categoryPage =
            categoryRepository.findByNameContainingIgnoreCase(
                requestDTO.getCategory(),
                pageable
            );
        if (categoryPage.isEmpty()) {
            throw new CategoryNotFoundException(
                "No categories found for the search term: " +
                requestDTO.getCategory()
            );
        }
        // maps the Page<Category> to Page<CategoryDto>
        // the mapper does not support page type conversion
        // so we need to map the content using the map method
        // we can rewrite the mapper to support page type conversion
        // but for now we will use the map method (much better)

        Page<CategoryDto> dtoPage = categoryPage.map(categoryMapper::toDto);
        return PagedResponseDTO.fromPage(dtoPage, dtoPage.getContent());
    }
}
