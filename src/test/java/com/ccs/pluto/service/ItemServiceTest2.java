package com.ccs.pluto.service;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.dto.ItemFormDto;
import com.ccs.pluto.models.entity.Item;
import com.ccs.pluto.models.entity.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest2 {

    @Mock
    ItemRepository itemRepository;

    @Mock
    ItemImgService itemImgService;

    @InjectMocks
    ItemService itemService;

    @Test
    @DisplayName("상품 등록 테스트")
    void saveItem() throws Exception {
        //given
        Item item = Item.builder()
                .stockNumber(1)
                .price(3)
                .itemName("양파")
                .itemSellStatus(ItemSellStatus.SELL)
                .itemDetail("Nope")
                .build();
        item.setId(20L);

        ItemFormDto MockItemFormDto = ItemFormDto.of(item);
        List<MultipartFile> itemImgFileList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MockMultipartFile multipartFile =
                    new MockMultipartFile("Test",
                            "image" + i + ".jpg",
                            "image/jpg",
                            new byte[]{1, 2, 3, 4});
            itemImgFileList.add(multipartFile);
        }

        when(itemRepository.save(any())).thenReturn(item);

        //when
        Long findId = itemService.saveItem(MockItemFormDto, itemImgFileList);

        //then
        assertThat(findId).isEqualTo(item.getId());
        verify(itemRepository, times(1)).save(any());
        verify(itemImgService, times(3)).saveItemImg(any(), any());
    }

    @Test
    @DisplayName("상품 수정(업데이트) 테스트")
    void updateItem() throws Exception {
        //given
        Item item = Item.builder()
                .stockNumber(1)
                .price(3)
                .itemName("양파")
                .itemSellStatus(ItemSellStatus.SELL)
                .itemDetail("Nope")
                .build();

        ItemFormDto MockItemFormDto = ItemFormDto.of(item);
        MockItemFormDto.setPrice(30);
        MockItemFormDto.setItemName("고구마");
        MockItemFormDto.setItemSellStatus(ItemSellStatus.SOLD_OUT);

        List<Long> itemImgIds = new ArrayList<>();
        List<MultipartFile> itemImgFileList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MockMultipartFile multipartFile =
                    new MockMultipartFile("Test",
                            "image" + i + ".jpg",
                            "image/jpg",
                            new byte[]{1, 2, 3, 4});
            itemImgFileList.add(multipartFile);
            itemImgIds.add((long) i);
        }
        MockItemFormDto.setItemImgIds(itemImgIds);

        when(itemRepository.findById(MockItemFormDto.getId())).thenReturn(Optional.of(item));

        //when
        itemService.updateItem(MockItemFormDto, itemImgFileList);

        //then
        assertThat(item.getItemName()).isEqualTo("고구마");
        assertThat(item.getPrice()).isEqualTo(30);
        assertThat(item.getItemSellStatus()).isEqualTo(ItemSellStatus.SOLD_OUT);
        verify(itemRepository, times(1)).findById(any());
        verify(itemImgService, times(3)).updateItemImg(any(), any());
    }
}
