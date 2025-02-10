package com.clubber.ClubberServer.global.common.page;

import java.util.List;
import org.springframework.data.domain.Page;

public record PageResponse<T>(
	List<T> content,
	int page,
	int size,
	long totalElements,
	int totalPages,
	boolean hasNextPage) {

	public static <T> PageResponse<T> of(Page<T> page) {
		return new PageResponse<>(
			page.getContent(),
			page.getNumber() + 1,
			page.getNumberOfElements(),
			page.getTotalElements(),
			page.getTotalPages(),
			page.hasNext());
	}
}
