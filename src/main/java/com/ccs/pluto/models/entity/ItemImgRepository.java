package com.ccs.pluto.models.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    //테스트용
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
