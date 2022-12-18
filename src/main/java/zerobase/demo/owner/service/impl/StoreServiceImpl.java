package zerobase.demo.owner.service.impl;

import static zerobase.demo.common.type.ResponseCode.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.exception.OwnerException;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.StoreOpenCloseStatus;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.owner.dto.CreateStore;
import zerobase.demo.owner.dto.OpenCloseStore;
import zerobase.demo.owner.dto.StoreInfo;
import zerobase.demo.owner.dto.UpdateStore;
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

		if(dto.getLoggedInUser() == null) throw new UserException(ResponseCode.NOT_LOGGED);

		User user = userRepository.findByUserId(dto.getOwnerId()).orElseThrow(() -> new UserException(USER_NOT_FOUND));

		UserDetails loggedInUser = dto.getLoggedInUser();
		if(!loggedInUser.getUsername().equals(dto.getOwnerId())) throw new OwnerException(NOT_AUTHORIZED);

		if(!loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OWNER")))
			throw new OwnerException(NOT_AUTHORIZED);

		Store newStore = Store.fromCreateStore(dto);
		newStore.setOpenClose(StoreOpenCloseStatus.CLOSE);
		newStore.setOpenCloseDt(LocalDateTime.now());
		newStore.setUser(user);

		storeRepository.save(newStore);
		return new CreateStore.Response(CREATE_STORE_SUCCESS);
	}

	@Override
	public OpenCloseStore.Response openCloseStore(OpenCloseStore dto) {

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

	@Override
	public StoreInfo.Response getStoreInfoByOwnerId(String ownerId) {

		User user = userRepository.findByUserId(ownerId).orElseThrow(() -> new UserException(USER_NOT_FOUND));
		if(user.getStatus() != UserStatus.OWNER) throw new OwnerException(NOT_OWNER);

		List<Store> storeList = storeRepository.findAllByUser(user);

		return new StoreInfo.Response(storeList, ResponseCode.SELECT_STORE_SUCCESS);
	}

	@Override
	public UpdateStore.Response updateStore(UpdateStore dto) {

		if(dto.getLoggedInUser() == null) throw new UserException(ResponseCode.NOT_LOGGED);

		Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(() -> new OwnerException(STORE_NOT_FOUND));

		UserDetails loggedInUser = dto.getLoggedInUser();
		if(!loggedInUser.getUsername().equals(store.getUser().getUserId())) throw new OwnerException(NOT_AUTHORIZED);

		store.setFromUpdateStoreDto(dto);
		storeRepository.save(store);

		return new UpdateStore.Response(UPDATE_STORE_SUCCESS);
	}
}
