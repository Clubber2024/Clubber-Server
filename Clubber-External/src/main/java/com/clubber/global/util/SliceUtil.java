package com.clubber.global.util;

import com.clubber.global.common.slice.SliceResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public class SliceUtil {

	public static <T> SliceResponse<T> valueOf(List<T> contents, Pageable pageable) {
		boolean hasNext = hasNext(contents, pageable);
		if (hasNext) {
			contents = getContents(contents, pageable);
		}
		return SliceResponse.of(contents, contents.size(), hasNext);
	}

	public static <T> boolean hasNext(List<T> contents, Pageable pageable) {
		return contents.size() > pageable.getPageSize();
	}

	private static <T> List<T> getContents(List<T> contents, Pageable pageable) {
		return contents.subList(0, pageable.getPageSize());
	}

	public static <T> T getLastContent(List<T> contents) {
		return contents.get(contents.size() - 2);
	}
}
