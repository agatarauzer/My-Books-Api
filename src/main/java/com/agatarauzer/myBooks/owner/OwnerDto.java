package com.agatarauzer.myBooks.owner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private String name;
    private OwnerType type;
}