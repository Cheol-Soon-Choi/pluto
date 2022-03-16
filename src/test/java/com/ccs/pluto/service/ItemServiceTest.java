package com.ccs.pluto.service;

import com.ccs.pluto.models.constant.ItemSellStatus;
import com.ccs.pluto.models.dto.AdminItemDto;
import com.ccs.pluto.models.dto.ItemFormDto;
import com.ccs.pluto.models.dto.ItemSearchDto;
import com.ccs.pluto.models.dto.MainItemDto;
import com.ccs.pluto.models.entity.Item;
import com.ccs.pluto.models.entity.ItemImg;
import com.ccs.pluto.models.entity.ItemImgRepository;
import com.ccs.pluto.models.entity.ItemRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

//    @Autowired
//    ItemImgService itemImgService;

    //    public ItemFormDto createItemFromDto() {
//        ItemFormDto itemFormDto = new ItemFormDto();
//        itemFormDto.setItemName("테스트 상품");
//        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
//        itemFormDto.setItemDetail("테스트 상품");
//        itemFormDto.setPrice(1000);
//        itemFormDto.setStockNumber(1000);
//        return itemFormDto;
//    }
//
//    List<MultipartFile> createMultipartFiles() {
//        List<MultipartFile> multipartFileList = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            String path = "C:/Users/CCS/Desktop/";
//            String imageName = "image" + i + ".jpg";
//            MockMultipartFile multipartFile =
//                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
//            multipartFileList.add(multipartFile);
//        }
//        return multipartFileList;
//    }
//
//    @Test
//    @DisplayName("상품 등록 테스트")
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void saveItem() throws Exception {
//        //given
//        ItemFormDto itemFormDto = createItemFromDto();
//        List<MultipartFile> multipartFileList = createMultipartFiles();
//
//        //when
//        Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
//        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
//        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
//
//        //then
//        assertThat(itemFormDto.getItemName()).isEqualTo(item.getItemName());
//        assertThat(itemFormDto.getItemSellStatus()).isEqualTo(item.getItemSellStatus());
//        assertThat(itemFormDto.getItemDetail()).isEqualTo(item.getItemDetail());
//        assertThat(itemFormDto.getPrice()).isEqualTo(item.getPrice());
//        assertThat(itemFormDto.getStockNumber()).isEqualTo(item.getStockNumber());
//        assertThat(multipartFileList.get(0).getOriginalFilename()).isEqualTo(itemImgList.get(0).getOriImgName());
//    }
//
//    @Test
//    @DisplayName("상품 수정 테스트")
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void updateItem() throws Exception {
//        //given
//        ItemFormDto itemFormDto = createItemFromDto();
//        List<MultipartFile> multipartFileList = createMultipartFiles();
//
//        //when
//        Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
//
//        itemFormDto = itemService.getItemDtl(itemId);
//
//        itemFormDto.setItemName("테스트 상품2");
//        itemFormDto.setItemSellStatus(ItemSellStatus.SOLD_OUT);
//        itemFormDto.setItemDetail("테스트 상품2");
//        itemFormDto.setPrice(2000);
//        itemFormDto.setStockNumber(2000);
//
//        List<Long> itemImgIds = new ArrayList<>();
//        List<MultipartFile> multipartFileList2 = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            String path = "C:/Users/CCS/Desktop/";
//            String imageName = "testimage" + i + ".jpg";
//            MockMultipartFile multipartFile2 =
//                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
//            multipartFileList2.add(multipartFile2);
//
//            itemImgIds.add(itemFormDto.getItemImgDtoList().get(i).getId());
//            itemFormDto.setItemImgIds(itemImgIds);
//        }
//
//        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
//        item.updateItem(itemFormDto);
//
//        for (int i = 0; i < multipartFileList2.size(); i++) {
//            Long itemImgId = itemFormDto.getItemImgIds().get(i);
//            itemImgService.updateItemImg(itemImgId, multipartFileList2.get(i));
//        }
//
//        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
//
//        //then
//        assertThat(itemFormDto.getItemName()).isEqualTo(item.getItemName());
//        assertThat(itemImgList.get(0).getOriImgName()).isEqualTo(multipartFileList2.get(0).getOriginalFilename());
//    }

    public void createItem() {
        String itemName = "축구공";
        String itemName2 = "농구공";
        int price = 10000;
        int stockNumber = 10;
        String itemDetail = "무회전 가능";

        for (int i = 0; i < 6; i++) {
            Item item = Item.builder()
                    .itemName(itemName + i)
                    .price(price + i)
                    .stockNumber(stockNumber + i)
                    .itemDetail(itemDetail + i)
                    .itemSellStatus(ItemSellStatus.SOLD_OUT)
                    .build();
            itemRepository.save(item);
            ItemImg itemImg = new ItemImg();
            itemImg.setRepimgYn("Y");
            itemImg.setItem(item);
            itemImg.updateItemImg("a", "b", "c");
            itemImgRepository.save(itemImg);

            Item item2 = Item.builder()
                    .itemName(itemName2 + i)
                    .price(price + i)
                    .stockNumber(stockNumber + i)
                    .itemDetail(itemDetail + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();
            itemRepository.save(item2);
            ItemImg itemImg2 = new ItemImg();
            itemImg2.setRepimgYn("N");
            itemImg2.setItem(item);
            itemImg2.updateItemImg("e", "f", "g");
            itemImgRepository.save(itemImg2);
        }
    }

    @Test
    @Order(1)
    @DisplayName("상품 세부정보 가져오기 테스트")
    public void getItemDtlTest() {
        //given
        createItem();

        //when
        Long id = itemRepository.findAll().get(0).getId();
        ItemFormDto itemFormDto = itemService.getItemDtl(id);

        //then
        assertThat(itemFormDto.getItemName()).isEqualTo("축구공0");
        assertThat(itemFormDto.getItemSellStatus()).isEqualTo(ItemSellStatus.SOLD_OUT);
        assertThat(itemFormDto.getItemImgDtoList().get(0).getImgName()).isEqualTo("b");
    }

    @Test
    @Order(2)
    @DisplayName("관리자 상품 검색 및 페이지 출력 테스트")
    public void getAdminItemPageTest() {
        //given
        Pageable pageable = PageRequest.of(0, 3);
        ItemSearchDto itemSearchDto = ItemSearchDto.builder()
                .searchSellStatus(ItemSellStatus.SELL)
                .searchDateType("1d")
                .searchQuery("")
                .searchBy("itemName")
                .build();

        createItem();

        //when
        Page<AdminItemDto> items = itemRepository.getAdminItemPage(itemSearchDto, pageable);

        //then
        assertThat(items.getTotalElements()).isEqualTo(6); // 총 데이터 개수
        assertThat(items.getTotalPages()).isEqualTo(2); // 총 페이지수
        for (int i = 0; i < 3; i++) {
            assertThat(items.getContent().get(i).getItemName()).contains("농구");
        } // 첫 페이지 농구공만 출력
    }

    @Test
    @Order(3)
    @DisplayName("메인 상품 검색 및 페이지 출력 테스트")
    public void getMainItemPageTest() {
        //given
        Pageable pageable = PageRequest.of(0, 6);
        ItemSearchDto itemSearchDto = ItemSearchDto.builder()
                .searchQuery("")
                .build();

        createItem();

        //when
        Page<MainItemDto> items = itemRepository.getMainItemPage(itemSearchDto, pageable);

        //then
        assertThat(items.getTotalElements()).isEqualTo(6); // 총 데이터 개수
        assertThat(items.getTotalPages()).isEqualTo(1); // 총 페이지수
    }
}