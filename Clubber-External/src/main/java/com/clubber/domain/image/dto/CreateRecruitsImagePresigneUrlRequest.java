package com.clubber.domain.image.dto;

import com.clubber.global.config.s3.ImageFileExtension;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateRecruitsImagePresigneUrlRequest {
    private List<ImageFileExtension> recruitImageExtensions = new ArrayList<>();
}
