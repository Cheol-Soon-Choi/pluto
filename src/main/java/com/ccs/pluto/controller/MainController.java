package com.ccs.pluto.controller;

import com.ccs.pluto.config.auth.dto.SessionUser;
import com.ccs.pluto.models.ProductRepository;
import com.ccs.pluto.models.UserRepository;
import com.ccs.pluto.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final HttpSession httpSession;

    //메인 페이지
    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
//        mav.addObject("users", userRepository.findAll());
        return mav;
    }

    //제품 페이지
    @GetMapping("/item")
    public ModelAndView item() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("view/product/item");
//        mav.addObject("users", userRepository.findAll());
        return mav;
    }

    //로그인 페이지
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("view/login");
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if(sessionUser != null){
            mav.addObject("userName", sessionUser.getName());
        }
        return mav;
    }
//    @GetMapping("/login")
//    public String login() {
//
//        return "/view/login";
//    }

    //회원가입
    @GetMapping("/member")
    public ModelAndView join() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("view/member/member");
        return mav;
    }

    @GetMapping("/callback")
    public ModelAndView callback() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("view/callback");
        return mav;
    }

    ////////////////////////////////////////

    //장바구니 페이지
    @GetMapping("/cart")
    public ModelAndView cart() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("view/cart");
//        mav.addObject("users", userRepository.findAll());
        return mav;
    }


    @GetMapping("/test")
    public String abcde(Model model) {
        model.addAttribute("testmsg", "테스트");
        return "view/testView";
    }



    //회원목록
    @GetMapping("/userList")
    public String list(Model model) {
        model.addAttribute("testmsg", "테스트");
        return "/view/userList";
    }

    //상품 등록
    @GetMapping("/addProduct")
    public String product(Model model) {
        model.addAttribute("testmsg", "테스트");
        return "/view/addProduct";
    }

}
