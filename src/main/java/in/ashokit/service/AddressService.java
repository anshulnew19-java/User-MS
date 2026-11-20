package in.ashokit.service;

import in.ashokit.dto.ShippingAddressDto;
import in.ashokit.entity.ShippingAddressEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.mapper.AddressMapper;
import in.ashokit.repository.AddressRepository;
import in.ashokit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private UserRepository userRepo;

    public ShippingAddressDto saveAddress(Integer userId, ShippingAddressDto shippingAddressDto) {
        ShippingAddressEntity addressEntity = AddressMapper.dtoToEntity(shippingAddressDto);
        Optional<UserEntity> byId = userRepo.findById(userId);
        if(byId.isPresent()) {
            UserEntity userEntity = byId.get();
            addressEntity.setUser(userEntity);
            ShippingAddressEntity saved = addressRepo.save(addressEntity);
            return AddressMapper.entityToDto(saved);
        }
        return null;
    }

    public ShippingAddressDto updateAddress(Integer addrId, ShippingAddressDto shippingAddressDto) {
        Optional<ShippingAddressEntity> byId = addressRepo.findById(addrId);
        if(byId.isPresent()) {
            ShippingAddressEntity addressEntity = byId.get();
            addressEntity.setCity(shippingAddressDto.getCity());
            addressEntity.setCountry(shippingAddressDto.getCountry());
            addressEntity.setHouseNum(shippingAddressDto.getHouseNum());
            addressEntity.setState(shippingAddressDto.getState());
            addressEntity.setZipcode(shippingAddressDto.getZipcode());
            addressEntity.setAddrType(shippingAddressDto.getAddrType());
            addressRepo.save(addressEntity);
            return AddressMapper.entityToDto(addressEntity);
        }
        return null;
    }

    public ShippingAddressDto deleteAddress(Integer addrId) {
        Optional<ShippingAddressEntity> byId = addressRepo.findById(addrId);
        if(byId.isPresent()) {
            addressRepo.deleteById(addrId);
            return AddressMapper.entityToDto(byId.get());
        }
        return null;
    }

    public List<ShippingAddressDto> getUserAddresses(Integer userId) {
        List<ShippingAddressEntity> entities = addressRepo.findByUserUserId(userId);
        return entities.stream()
                .map(AddressMapper::entityToDto)
                .toList();
    }
}
