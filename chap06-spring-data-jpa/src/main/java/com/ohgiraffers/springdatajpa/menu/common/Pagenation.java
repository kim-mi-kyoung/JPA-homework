package com.ohgiraffers.springdatajpa.menu.common;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter @Setter @ToString
public class Pagenation {

    public static PagingButtonInfo getPagingButtonInfo(Page<MenuDTO>menuList) {

        int currentPage = menuList.getNumber() + 1;
        int defaultButtonCount = 10;
        int startPage = (int) (Math.ceil((double) currentPage / defaultButtonCount) -1 )
                               * defaultButtonCount + 1;
        int endPage = startPage + defaultButtonCount - 1;


        /* 메뉴 리스트의 총 페이지 수가 endPage 보다 적을 경우 endPage = 총 페이지 수 */
        if (menuList.getTotalPages() < endPage) endPage = menuList.getTotalPages();

        /* 컨텐츠가 없을 경우 페이지는 0이 아닌 1이 되도록 */
        if (menuList.getTotalPages() == 0 && endPage == 0) endPage = startPage;

        return new PagingButtonInfo(currentPage, startPage, endPage);
    }
}
