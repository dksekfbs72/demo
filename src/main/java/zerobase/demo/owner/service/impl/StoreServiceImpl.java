package zerobase.demo.owner.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.demo.entity.Store;
import zerobase.demo.entity.User;
import zerobase.demo.exception.AlreadyOpenClosedException;
import zerobase.demo.exception.NonExistentStoreException;
import zerobase.demo.exception.NonExistentUserException;
import zerobase.demo.exception.NotAuthorizedException;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.StoreService;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;

	@Override
	public void createStore(CreateStore createStore) {

		//임시 하드코딩
		User user = new User();
		user.setId(1);
		user.setStatus("owner");

		//원래 request.ownerId로 조회 해 와야함
		Optional<User> optionalUser = Optional.of(user);

		//owner 검증
		if(!optionalUser.isPresent()) throw new NonExistentUserException();
		if(!Objects.equals(user.getStatus(), "owner")) throw new NotAuthorizedException();

		Store newStore = Store.fromDto(createStore);
		newStore.setOpenClose(false);
		newStore.setUser(user);
		newStore.setRegDt(LocalDateTime.now());

		storeRepository.save(newStore);
	}

	@Override
	public void openCloseStore(OpenCloseStore openCloseStoret) {

		Optional<Store> optionalStore = storeRepository.findById(openCloseStoret.getId());
		if(!optionalStore.isPresent()) throw new NonExistentStoreException();

		Store nowStore = optionalStore.get();
		if(openCloseStoret.getOpenClose() == nowStore.getOpenClose()) throw new AlreadyOpenClosedException();

		nowStore.setOpenClose(!nowStore.getOpenClose());
		nowStore.setOpenCloseDt(LocalDateTime.now());
		storeRepository.save(nowStore);
	}

	@Override
	public List<StoreInfo> getStoreInfoByOwnerId(int ownerId) {

		//임시 하드코딩
		User user = new User();
		user.setId(ownerId);
		user.setStatus("owner");

		//원래 request.ownerId로 조회 해 와야함
		Optional<User> optionalUser = Optional.of(user);

		//owner 검증
		if(!optionalUser.isPresent()) throw new NonExistentUserException();
		if(!Objects.equals(user.getStatus(), "owner")) throw new NotAuthorizedException();

		return StoreInfo.fromEntity(storeRepository.findAllByUser(user));
	}

	@Override
	public void updateStore(UpdateStore updateStore) {

		Optional<Store> optionalStore = storeRepository.findById(updateStore.getId());
		if(!optionalStore.isPresent()) throw new NonExistentStoreException();

		Store store = optionalStore.get();
		store.setName(updateStore.getName());
		store.setStoreAddr(updateStore.getStoreAddr());
		store.setPictureUrl(updateStore.getPictureUrl());
		store.setDeliveryDistanceKm(updateStore.getDeliveryDistanceKm());
		store.setSummary(updateStore.getSummary());
		store.setDeliveryTip(updateStore.getDeliveryTip());
		store.setCommission(updateStore.getCommission());

		store.setUdtDt(LocalDateTime.now());

		storeRepository.save(store);
	}
}
