package com.agatarauzer.myBooks.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable final Long ownerId) {
        Owner owner = ownerService.findOwnerById(ownerId);
        return ResponseEntity.ok(ownerMapper.mapToOwnerDto(owner));
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody final OwnerDto ownerDto) {
        Owner owner = ownerMapper.mapToOwner(ownerDto);
        ownerService.updateOwner(owner);
        return ResponseEntity.ok(ownerDto);
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<String> deleteOwner(@PathVariable final Long ownerId) {
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.ok().body("Deleted user with id: " + ownerId);
    }
}
