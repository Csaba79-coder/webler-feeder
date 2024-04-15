package hu.webler.weblerfeeder.address.controller;

import hu.webler.weblerfeeder.address.model.AddressModel;
import hu.webler.weblerfeeder.address.model.AddressModelUpdateCreate;
import hu.webler.weblerfeeder.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressModel>> listAllAddress() {
        return ResponseEntity.status(200).body(addressService.getAllAddress()) ;
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressModel> addAddress(@RequestBody AddressModelUpdateCreate addressModelUpdateCreate) {
        return ResponseEntity.status(200).body(addressService.addAddress(addressModelUpdateCreate));
    }

    @DeleteMapping("/addresses/address/id/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/addresses/address/id/{id}")
    public ResponseEntity<AddressModel> updateAddress(@PathVariable Long id, @RequestBody AddressModelUpdateCreate addressModelUpdateCreate) {
        return ResponseEntity.status(200).body(addressService.updateAddress(id, addressModelUpdateCreate));
    }
}
