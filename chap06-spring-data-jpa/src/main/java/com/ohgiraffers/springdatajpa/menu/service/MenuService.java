package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repsitory.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repsitory.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    /* 1. id로 entity 조회 findById(id) */
    public MenuDTO findMenuByCode(int menuCode) {

        // Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해 반환 받는다
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(menu, MenuDTO.class);
    }

    /* 2. 모든 entity 조회 : findAll() */
    public List<MenuDTO> findMenuList() {

        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());

    }

    /* 2-2 페이징된 entity 조회 : findAll(Pageable) */
    public Page<MenuDTO> findMenuList(Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1,
                pageable.getPageSize(), Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }

    /* Query Method Test*/
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    /* JPQL or Native Query Test */
    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    /* 5. Entity 저장 */
    @Transactional
    public void registerNewMenu(MenuDTO menu) {

        menuRepository.save(modelMapper.map(menu, Menu.class));
    }

    /* 6. Entity 수정 */
    @Transactional
    public void modifyMenu(MenuDTO menu) {
        Menu foundMenu = menuRepository.findById(menu.getMenuCode())
                .orElseThrow(IllegalArgumentException::new);

        foundMenu.setMenuName(menu.getMenuName());
    }

    /* 7. Entity 삭제 */
    @Transactional
    public void deleteMenu(Integer menuCode) {

        menuRepository.deleteById(menuCode);
    }

    public List<MenuDTO> searchMenuByPrice(Integer menuPrice1, Integer menuPrice2) {

        List<Menu> menuList = menuRepository.findByMenuPriceBetween(menuPrice1, menuPrice2);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    public List<MenuDTO> searchMenuByKeyword(String keyword) {

        List<Menu> menuList = menuRepository.findByMenuNameContaining(keyword);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    public List<CategoryDTO> searchCategoriesByRefCode(Integer refCategoryCode) {

        List<Category> categoryList = categoryRepository.searchCategoryByRefCode(refCategoryCode);

        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

}
