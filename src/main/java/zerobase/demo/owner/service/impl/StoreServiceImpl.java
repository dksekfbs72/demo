package zerobase.demo.owner.service.impl;

import static zerobase.demo.common.type.ResponseCode.*;

import java.time.LocalDateTime;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.OwnerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.StoreOpenCloseStatus;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.owner.service.StoreService;
import zerobase.demo.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;
	private final UserRepository userRepository;

	@Override
	public CreateStore.Response createStore(CreateStore dto) {

		if(dto.getLoggedInUser() == null) throw new UserException(NOT_LOGGED_IN);

		User user = userRepository.findByUserId(dto.getOwnerId()).orElseThrow(() -> new UserException(USER_NOT_FIND));

		UserDetails loggedInUser = dto.getLoggedInUser();
		if(!loggedInUser.getUsername().equals(dto.getOwnerId())) throw new OwnerException(NOT_AUTHORIZED);

		if(!loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER")))
			throw new OwnerException(NOT_AUTHORIZED);

		Store newStore = Store.fromDto(dto);
		newStore.setOpenClose(StoreOpenCloseStatus.CLOSE);
		newStore.setOpenCloseDt(LocalDateTime.now());
		newStore.setUser(user);

		storeRepository.save(newStore);
		return new CreateStore.Response(CREATE_STORE_SUCCESS);
	}

	@Override
	public OpenCloseStore.Response openCloseStore(OpenCloseStore dto) {

		if(dto.getLoggedInUser() == null) throw new UserException(NOT_LOGGED_IN);

		Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(() -> new OwnerException(STORE_NOT_FOUND));

		if(!store.getUser().getUserId().equals(dto.getLoggedInUser().getUsername()))
			throw new OwnerException(NOT_AUTHORIZED);

		if(store.getOpenClose() == dto.getOpenClose()) {
			if(store.getOpenClose() == StoreOpenCloseStatus.OPEN) throw new OwnerException(ALREADY_OPEN);
			if(store.getOpenClose() == StoreOpenCloseStatus.CLOSE) throw new OwnerException(ALREADY_CLOSE);
		}

		store.setOpenClose(dto.getOpenClose());
		store.setOpenCloseDt(LocalDateTime.now());

		storeRepository.save(store);

		if(dto.getOpenClose() == StoreOpenCloseStatus.OPEN)
			return new OpenCloseStore.Response(OPEN_STORE_SUCCESS);
		else
			return new OpenCloseStore.Response(CLOSE_STORE_SUCCESS);
	}

	// @Override
	// public List<StoreInfo> getStoreInfoByOwnerId(int ownerId) {
	//
	// 	//임시 하드코딩
	// 	User user = new User();
	// 	user.setId(ownerId);
	// 	user.setStatus(UserStatus.OWNER);
	//
	// 	//원래 request.ownerId로 조회 해 와야함
	// 	Optional<User> optionalUser = Optional.of(user);
	//
	// 	//owner 검증
	// 	if(!optionalUser.isPresent()) throw new NonExistentUserException(user.getId());
	// 	if(user.getStatus() != UserStatus.OWNER) throw new NotAuthorizedException();
	//
	// 	return StoreInfo.fromEntity(storeRepository.findAllByUser(user));
	// }
	//
	// @Override
	// public void updateStore(UpdateStore updateStore) {
	//
	// 	Optional<Store> optionalStore = storeRepository.findById(updateStore.getId());
	// 	if(!optionalStore.isPresent()) throw new NonExistentStoreException(updateStore.getId());
	//
	// 	//현재 로그인 된 유저가 점포 주인인지 확인하는 부분 추가 예정(Spring security 사용 예정)
	//
	// 	Store store = optionalStore.get();
	// 	store.setName(updateStore.getName());
	// 	store.setStoreAddr(updateStore.getStoreAddr());
	// 	store.setPictureUrl(updateStore.getPictureUrl());
	// 	store.setDeliveryDistanceKm(updateStore.getDeliveryDistanceKm());
	// 	store.setSummary(updateStore.getSummary());
	// 	store.setDeliveryTip(updateStore.getDeliveryTip());
	// 	store.setCommission(updateStore.getCommission());
	//
	// 	storeRepository.save(store);
	// }
}
