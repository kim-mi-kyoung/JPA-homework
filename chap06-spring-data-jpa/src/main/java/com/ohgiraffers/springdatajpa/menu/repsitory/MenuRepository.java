package com.ohgiraffers.springdatajpa.menu.repsitory;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository <Menu, Integer> {

    /* 전달 받은 menuPrice를 초과하는 가격의 메뉴 조회 */
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    /* 정달 받은 menuPrice를 초과하는 가격의 메뉴 조회 (가격 오름차순 정렬) */
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

    /* 전달 받은 menuPrice를 초과하는 가격의 메뉴 조회 (정렬 기준 전달인자로 제시) */
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);

    List<Menu> findByCategoryCode(Integer menuCode);

    /* =============== 과제 =============== */
    List<Menu> findByMenuPriceBetween(Integer menuPrice1, Integer menuPrice2);

    List<Menu> findByMenuNameContaining(String keyword);

    /* JPQL 사용한 쿼리 작성 */




}
