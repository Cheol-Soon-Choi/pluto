//package com.ccs.pluto.service;
//
//import com.ccs.pluto.models.constant.ItemSellStatus;
//import com.ccs.pluto.models.dto.ItemFormDto;
//import com.ccs.pluto.models.entity.Item;
//import com.ccs.pluto.models.entity.ItemImg;
//import com.ccs.pluto.models.entity.ItemImgRepository;
//import com.ccs.pluto.models.entity.ItemRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@ExtendWith(SpringExtension.class)
//class ItemServiceTest {
//
//    @Autowired
//    ItemService itemService;
//
//    @Autowired
//    ItemRepository itemRepository;
//
//    @Autowired
//    ItemImgRepository itemImgRepository;
//
//    @Autowired
//    ItemImgService itemImgService;
//
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
//}