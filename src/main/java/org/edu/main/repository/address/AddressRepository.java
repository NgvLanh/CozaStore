package org.edu.main.repository.address;

import org.edu.main.model.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressByActiveTrueAndUserId(long userId);
}
