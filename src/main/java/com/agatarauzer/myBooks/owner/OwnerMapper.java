package com.agatarauzer.myBooks.owner;

import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    public Owner mapToOwner(OwnerDto ownerDto) {
        return Owner.builder()
                .name(ownerDto.getName())
                .type(ownerDto.getType())
                .build();
    }

    public OwnerDto mapToOwnerDto(Owner owner) {
        return OwnerDto.builder()
                .name(owner.getName())
                .type(owner.getType())
                .build();
    }
}