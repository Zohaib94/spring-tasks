package com.devtiro.tasks.domain.dto;

public record ErrorResponseDto(
  int status,
  String message,
  String detail
) {}
