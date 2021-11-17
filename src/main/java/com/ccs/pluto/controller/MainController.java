package com.ccs.pluto.controller;

import com.ccs.pluto.models.dto.*;
import com.ccs.pluto.models.entity.Item;
import com.ccs.pluto.service.CartService;
import com.ccs.pluto.service.ItemService;
import com.ccs.pluto.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final CartService cartService;

    //메인 페이지
    @GetMapping("/")
    public String home(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.orElse(0), 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "index";
    }

    //회원가입 페이지
    @GetMapping("/members")
    public String memberForm() {
        return "member/member";
    }

    //로그인 페이지
    @GetMapping("/login")
    public String loginMember() {
        return "member/memberLogin";
    }

    //로그인 페이지
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLogin";
    }

    //관리자 제품 등록 페이지
    @GetMapping("/admin/items")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    //관리자 제품 관리 페이지
    @GetMapping(value = {"/admin/itemMng", "/admin/itemMng/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemMng";
    }

    //주문 내역 페이지
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {

        System.out.println(principal);
        System.out.println(principal.getName());

        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("orders", ordersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/orderHist";
    }

    //주문목록 페이지
    @GetMapping("/cart")
    public String orderHist(Principal principal, Model model){
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";
    }
}