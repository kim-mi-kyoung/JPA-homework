package com.ohgiraffers.springdatajpa.menu.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository <Category, Integer> {

    /* Category 테이블의 모든 entity를 조회하기 위해 별도의 JPQL 정의는 필요 없으나
    * 예제를 위해 테스트 코드 작성 (findAll 기능으로 조회) */


    /* JPQL */
//    @Query(value = "SELECT c FROM Category c ORDER BY c.categoryCode")

    /* Native */
    @Query(value = "SELECT CATEGORY_CODE, CATEGORY_NAME, REF_CATEGORY_CODE " +
                   "FROM TBL_CATEGORY ORDER BY CATEGORY_CODE",
                    nativeQuery = true)

    public List<Category> findAllCategory();

    @Query(value = "SELECT c FROM Category c WHERE c.refCategoryCode = :refCategoryCode ORDER BY c.categoryCode")
    public List<Category> searchCategoryByRefCode(Integer refCategoryCode);

}
