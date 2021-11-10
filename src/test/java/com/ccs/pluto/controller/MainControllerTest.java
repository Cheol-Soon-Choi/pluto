package com.ccs.pluto.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("ADMIN 권한 접근 확인")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void authTest1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/items"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("USER 권한 접근 확인")
    @WithMockUser(username = "user", roles = "USER")
    public void authTest2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/items"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}