package com.clubber.global.config.swagger;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExampleHolder {

	private Example holder;
	private String name;
	private int code;
}
