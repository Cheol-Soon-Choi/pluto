package com.ccs.pluto.controller;

import com.ccs.pluto.models.dto.ItemFormDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class ItemApiController {

    @PostMapping("/admin/items")
    public Long newItem(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, HttpServletResponse httpServletResponse) throws IOException {

        if (bindingResult.hasErrors()) {
            httpServletResponse.sendRedirect("/admin/items");
        }

        return null;
    }
}
