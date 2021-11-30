package com.ccs.pluto.controller;

import com.ccs.pluto.models.dto.ItemFormDto;
import com.ccs.pluto.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    //상품 등록
    @PostMapping("/admin/items")
    public ModelAndView newItem(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                                @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/");

        if (bindingResult.hasErrors()) {
            mav.setViewName("item/itemForm");
            return mav;
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            mav.setViewName("item/itemForm");
            mav.addObject("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return mav;
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            mav.setViewName("item/itemForm");
            mav.addObject("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return mav;
        }
        return mav;
    }

    //상품 수정(정보가져오기)
    @GetMapping("/admin/items/{itemId}")
    public ModelAndView getItemDtl(@PathVariable("itemId") Long itemId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("item/itemForm");

        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            mav.addObject("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            mav.addObject("errorMessage", "존재하지 않는 상품입니다.");
            mav.addObject("itemFormDto", new ItemFormDto());
        }

        return mav;
    }

    //상품 수정
    @PostMapping("/admin/items/{itemId}")
    public ModelAndView editItem(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                                 @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/");

        if (bindingResult.hasErrors()) {
            mav.setViewName("item/itemForm");
            return mav;
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            mav.setViewName("item/itemForm");
            mav.addObject("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return mav;
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            mav.setViewName("item/itemForm");
            mav.addObject("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return mav;
        }

        return mav;
    }

    //상품 상세정보
    @GetMapping("/items/{itemId}")
    public ModelAndView itemDtl(@PathVariable("itemId") Long itemId) {

        ModelAndView mav = new ModelAndView();
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        mav.addObject("item", itemFormDto);
        mav.setViewName("item/itemDtl");

        return mav;
    }

}
