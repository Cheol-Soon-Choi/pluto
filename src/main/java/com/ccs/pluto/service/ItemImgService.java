package com.ccs.pluto.service;

import com.ccs.pluto.models.entity.ItemImg;
import com.ccs.pluto.models.entity.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    private final ItemImgRepository itemImgRepository;

    private final S3Service s3Service;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드(S3)
        if (!StringUtils.isEmpty(oriImgName)) {
            UUID uuid = UUID.randomUUID();
            String extension = oriImgName.substring(oriImgName.lastIndexOf("."));
            imgName = uuid + extension;

            imgUrl = s3Service.upload(imgName, itemImgFile);
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if (!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                s3Service.delete(savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String extension = oriImgName.substring(oriImgName.lastIndexOf("."));
            String imgName = uuid + extension;
            String imgUrl = s3Service.upload(imgName, itemImgFile);

            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }
}