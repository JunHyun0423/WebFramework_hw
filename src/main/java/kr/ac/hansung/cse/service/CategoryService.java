package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 같은 이름이 없을 때만 카테고리를 저장한다.
    @Transactional
    public Category createCategory(String name) {
        String categoryName = name.trim();

        categoryRepository.findByName(categoryName)
                .ifPresent(category -> {
                    throw new DuplicateCategoryException(categoryName);
                });

        return categoryRepository.save(new Category(categoryName));
    }

    // 상품이 연결된 카테고리는 삭제하지 못하게 처리한다.
    @Transactional
    public void deleteCategory(Long id) {
        long productCount = categoryRepository.countProductsByCategoryId(id);

        if (productCount > 0) {
            throw new IllegalStateException(
                    "상품 " + productCount + "개가 연결되어 있어 삭제할 수 없습니다.");
        }

        categoryRepository.delete(id);
    }
}
