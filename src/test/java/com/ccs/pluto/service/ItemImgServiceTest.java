package com.ccs.pluto.service;

import com.ccs.pluto.models.entity.ItemImg;
import com.ccs.pluto.models.entity.ItemImgRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemImgServiceTest {

    @Mock
    ItemImgRepository itemImgRepository;

    @Mock
    S3Service s3Service;

    @InjectMocks
    ItemImgService itemImgService;

    @Test
    @DisplayName("제품 이미지 저장")
    public void saveItemImgTest() throws Exception {
        //given
        MockMultipartFile itemImgFile =
                new MockMultipartFile("z", "a1.jpg", "image/jpg", new byte[]{1, 2, 3, 4});
        ItemImg itemImg = new ItemImg();

        //when
        itemImgService.saveItemImg(itemImg, itemImgFile);

        //then
        assertThat(itemImg.getOriImgName()).isEqualTo("a1.jpg");
        verify(itemImgRepository, times(1)).save(any());
        verify(s3Service, times(1)).upload(any(), any());
    }

    @Test
    @DisplayName("제품 이미지 업데이트")
    public void updateItemImgTest() throws Exception {
        //given
        MockMultipartFile itemImgFile =
                new MockMultipartFile("z", "b1.jpg", "image/jpg", new byte[]{1, 2, 3, 4});
        ItemImg itemImg = new ItemImg();
        itemImg.setImgName("test");

        when(itemImgRepository.findById(any())).thenReturn(Optional.of(itemImg));

        //when
        itemImgService.updateItemImg(2L, itemImgFile);

        //then
        assertThat(itemImg.getOriImgName()).isEqualTo("b1.jpg");
        verify(s3Service, times(1)).delete(any());
        verify(s3Service, times(1)).upload(any(), any());
    }
}