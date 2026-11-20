package in.ashokit.rest;

import in.ashokit.dto.ApiResponse;
import in.ashokit.dto.ShippingAddressDto;
import in.ashokit.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class AddressRestController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/{userId}/shipping-address")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> saveShippingAddress(@RequestBody ShippingAddressDto shippingAddressDto, @PathVariable Integer userId) {
        ShippingAddressDto addressDto = addressService.saveAddress(userId, shippingAddressDto);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Shipping address saved successfully");
        response.setData(addressDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{addrId}/shipping-address")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> updateShippingAddress(@RequestBody ShippingAddressDto shippingAddressDto, @PathVariable Integer addrId) {
        ShippingAddressDto addressDto = addressService.updateAddress(addrId, shippingAddressDto);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Shipping address updated successfully");
        response.setData(addressDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/shipping-address/{addrId}")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> deleteShippingAddress(@PathVariable Integer addrId) {
        ShippingAddressDto deletedAddress = addressService.deleteAddress(addrId);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Shipping address deleted successfully");
        response.setData(deletedAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/shipping-addresses/{userId}")
    public ResponseEntity<ApiResponse<List<ShippingAddressDto>>> getCustomerAddresses(@PathVariable Integer userId) {
        List<ShippingAddressDto> userAddresses = addressService.getUserAddresses(userId);
        ApiResponse<List<ShippingAddressDto>> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Addresses of User fetched successfully");
        response.setData(userAddresses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
