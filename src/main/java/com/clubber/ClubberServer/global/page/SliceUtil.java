package com.clubber.ClubberServer.global.page;

import java.util.List;

import org.springframework.data.domain.Pageable;

public class SliceUtil {
	

	private static <T> boolean hasNext(List<T> contents, Pageable pageable){
		return contents.size() > pageable.getPageSize();
	}

	private static <T> List<T> getContents(List<T> contents, Pageable pageable){
		return contents.subList(0, pageable.getPageSize());
	}
}
