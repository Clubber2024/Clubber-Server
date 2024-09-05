package com.clubber.ClubberServer.global.page;

import java.util.List;

public record SliceResponse<T>(List<T> content, long size, boolean hasNext) {
	public static <T> SliceResponse<T> of(List<T> content, long size, boolean hasNext){
		return new SliceResponse<>(
			content,
			size,
			hasNext);
	}
}
