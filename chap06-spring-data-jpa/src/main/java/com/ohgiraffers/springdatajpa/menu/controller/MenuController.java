package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.common.Pagenation;
import com.ohgiraffers.springdatajpa.menu.common.PagingButtonInfo;
import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
@Slf4j
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{menuCode}")
    public String findenuByCode(@PathVariable int menuCode, Model model) {

        MenuDTO menu = menuService.findMenuByCode(menuCode);

        model.addAttribute("menu", menu);

        return "menu/detail";
    }


    /* 페이징 처리 X */
//    @GetMapping("/list")
//    public String findMenuList(Model model) {
//
//        List<MenuDTO> menuList = menuService.findMenuList();
//
//        model.addAttribute("menuList", menuList);
//
//        return "menu/list";
//    }


    /* 페이징 처리 O */
    @GetMapping("/list")
    public String findMenuList(@PageableDefault Pageable pageable, Model model) {

        /* page -> number, size, sort 파라미터가 Pageable 객체에 담긴다. */
        log.info("pageable : {}", pageable);

        Page<MenuDTO> menuList = menuService.findMenuList(pageable);

        log.info("조회한 내용 : {}", menuList.getContent());
        log.info("총 페이지 수 : {}", menuList.getTotalPages());
        log.info("총 메뉴 수 : {}", menuList.getTotalElements());
        log.info("해당 페이지에 표시될 요소의 수 : {}", menuList.getSize());
        log.info("해당 페이지의 실제 요소의 수 : {}", menuList.getNumberOfElements());
        log.info("첫 페이지 여부: {}", menuList.isFirst());
        log.info("마지막 페이지 여부: {}", menuList.isLast());
        log.info("정렬 방식: {}", menuList.getSort());
        log.info("여러 페이지 중 현재 인덱스 : {}", menuList.getNumber());

        PagingButtonInfo paging = Pagenation.getPagingButtonInfo(menuList);

        model.addAttribute("menuList", menuList);
        model.addAttribute("paging", paging);

        return "menu/list";
    }

    @GetMapping("/queryMethod")
    public String queryMethodPage() {
        return "/menu/methods/queryMethod";
    }

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model) {

        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);

        model.addAttribute("menuList", menuList);

        return "menu/searchResult";
    }

    @GetMapping("/register")
    public void registerPage() {
    }

    @GetMapping("/category")
    @ResponseBody
    public List<CategoryDTO> findCategoryList() {

        return menuService.findAllCategory();
    }

    @PostMapping("/register")
    public String registerMenu(MenuDTO menu) {

        menuService.registerNewMenu(menu);

        return "redirect:/menu/list";
    }

    @GetMapping("/modify")
    public void modifyPage() {}

    @PostMapping("/modify")
    public String modifyMenu(MenuDTO menu) {

        menuService.modifyMenu(menu);


        return "redirect:/menu/" + menu.getMenuCode();
    }

    @GetMapping("/delete")
    public void deletePage() {}

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam Integer menuCode) {

        menuService.deleteMenu(menuCode);

        return "redirect:/menu/list";
    }

    /* ============ 과제 ============ */

    /* 특정 가격대에 해당하는 메뉴 검색하기 */
    @GetMapping("/searchByPrice")
    public String searchPage() {
        return "/menu/methods/searchMethod";
    }

    @GetMapping("/searchResult")
    public String searchByPrice(@RequestParam Integer menuPrice1, @RequestParam Integer menuPrice2, Model model) {

        List<MenuDTO> menuList = menuService.searchMenuByPrice(menuPrice1, menuPrice2);

        log.info("menuList : {}", menuList);

        model.addAttribute("menuList", menuList);

        return "/menu/searchByPrice";
    }

    /* 특정 단어가 포함된 메뉴 이름 검색하기 */
    @GetMapping("/searchByKeyword")
    public String searchByKeyword() {
        return "/menu/methods/searchByKeyword";
    }

    @GetMapping("/nameSearchResult")
    public String searchByKeyword(@RequestParam String keyword, Model model) {

        List<MenuDTO> menuList = menuService.searchMenuByKeyword(keyword);
        log.info("키워드로 조회한 menuList : {}", menuList);

        model.addAttribute("menuList", menuList);

        return "/menu/nameSearchResult";
    }

    /* 상위 카테고리 코드로 해당하는 카테고리 조회하기 */

    @GetMapping("/searchByRefCode")
    public String searchByRefCode() {
        return "/menu/methods/searchByRefCode";
    }

    @GetMapping("/categoriesByRefCode")
    public String searchCategoriesByRefCode(@RequestParam Integer refCategoryCode, Model model) {

        List<CategoryDTO> categoryList = menuService.searchCategoriesByRefCode(refCategoryCode);

        log.info("조회된 category : {}", categoryList);

        model.addAttribute("categoryList", categoryList);

        return "/menu/categoryList";
    }

}
