package org.edu.main.repository;

import org.edu.main.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAddressByActiveTrueAndUserId(long userId);
}
