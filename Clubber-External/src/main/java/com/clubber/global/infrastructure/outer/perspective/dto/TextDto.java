package com.clubber.global.infrastructure.outer.perspective.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TextDto {

	@Schema(description = "분석하고자하는 텍스트", example = "악성 댓글")
	private String text;
}
