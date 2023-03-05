package com.agatarauzer.myBooks.owner;

import com.agatarauzer.myBooks.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

	private final OwnerRepository ownerRepository;

	public Owner findOwnerById(Long ownerId) {
		return ownerRepository.findById(ownerId).orElseThrow(() -> new EntityNotFoundException("Owner id not found: " + ownerId));
	}

	public Owner saveOwner(Owner owner) {
		return ownerRepository.save(owner);
	}
	
	public Owner updateOwner(Owner owner) {
		ownerRepository.findById(owner.getId())
			.orElseThrow(() -> new EntityNotFoundException("Owner id not found: " + owner.getId()));
		saveOwner(owner);
		log.info("Owner with id: " + owner.getId() + " was updated");
		return owner;
	}
	
	public void deleteOwner(Long ownerId) {
		ownerRepository.findById(ownerId)
			.orElseThrow(() -> new EntityNotFoundException("Owner id not found: " + ownerId));
		ownerRepository.deleteById(ownerId);
		log.info("Owner with id: " + ownerId + " was deleted");
	}
}