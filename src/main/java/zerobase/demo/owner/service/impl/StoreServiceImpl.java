package zerobase.demo.owner.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.AlreadyOpenClosedException;
import zerobase.demo.common.exception.NonExistentStoreException;
import zerobase.demo.common.exception.NonExistentUserException;
import zerobase.demo.common.exception.NotAuthorizedException;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.StoreService;
import zerobase.demo.common.type.StoreOpenCloseStatus;
import zerobase.demo.common.type.UserStatus;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;

	@Override
	public void createStore(CreateStore createStore) {

		//임시 하드코딩
		User user = new User();
		user.setId(1);
		user.setStatus(UserStatus.OWNER);

		//원래 request.ownerId로 조회 해 와야함
		Optional<User> optionalUser = Optional.of(user);

		//owner 검증
		if(!optionalUser.isPresent()) throw new NonExistentUserException(user.getId());
		if(user.getStatus() != UserStatus.OWNER) throw new NotAuthorizedException();

		Store newStore = Store.fromDto(createStore);
		newStore.setOpenClose(StoreOpenCloseStatus.CLOSE);
		newStore.setOpenCloseDt(LocalDateTime.now());
		newStore.setUser(user);

		storeRepository.save(newStore);
	}

	@Override
	public void openCloseStore(OpenCloseStore openCloseStore) {

		Optional<Store> optionalStore = storeRepository.findById(openCloseStore.getId());
		if(!optionalStore.isPresent()) throw new NonExistentStoreException(openCloseStore.getId());

		Store nowStore = optionalStore.get();
		if(openCloseStore.getOpenClose() == nowStore.getOpenClose()) throw new AlreadyOpenClosedException();

		nowStore.setOpenClose(openCloseStore.getOpenClose());
		nowStore.setOpenCloseDt(LocalDateTime.now());
		storeRepository.save(nowStore);
	}

	@Override
	public List<StoreInfo> getStoreInfoByOwnerId(int ownerId) {

		//임시 하드코딩
		User user = new User();
		user.setId(ownerId);
		user.setStatus(UserStatus.OWNER);

		//원래 request.ownerId로 조회 해 와야함
		Optional<User> optionalUser = Optional.of(user);

		//owner 검증
		if(!optionalUser.isPresent()) throw new NonExistentUserException(user.getId());
		if(user.getStatus() != UserStatus.OWNER) throw new NotAuthorizedException();

		return StoreInfo.fromEntity(storeRepository.findAllByUser(user));
	}

	@Override
	public void updateStore(UpdateStore updateStore) {

		Optional<Store> optionalStore = storeRepository.findById(updateStore.getId());
		if(!optionalStore.isPresent()) throw new NonExistentStoreException(updateStore.getId());

		//현재 로그인 된 유저가 점포 주인인지 확인하는 부분 추가 예정(Spring security 사용 예정)

		Store store = optionalStore.get();
		store.setName(updateStore.getName());
		store.setStoreAddr(updateStore.getStoreAddr());
		store.setPictureUrl(updateStore.getPictureUrl());
		store.setDeliveryDistanceKm(updateStore.getDeliveryDistanceKm());
		store.setSummary(updateStore.getSummary());
		store.setDeliveryTip(updateStore.getDeliveryTip());
		store.setCommission(updateStore.getCommission());

		storeRepository.save(store);
	}
}
