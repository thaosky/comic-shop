package com.example.demo.repository;

import com.example.demo.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByPhoneNumberAndName(String phone, String name);

    @Query(nativeQuery = true,
            value = "select * from customer c\n" +
                    "where (UPPER(c.name) like CONCAT('%',:name,'%') or :name is null)\n" +
                    "and (c.phone_number like CONCAT('%',:phoneNumber,'%') or :phoneNumber is null)",
            countQuery = "select count(*) from customer c\n" +
                    "where (UPPER(c.name) like CONCAT('%',:name,'%') or :name is null)\n" +
                    "and (c.phone_number like CONCAT('%',:phoneNumber,'%') or :phoneNumber is null)")
    Page<CustomerEntity> listCustomer(String phoneNumber, String name, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT c.* from  customer c\n" +
                    "left join rent r on c.id = r.customer_id\n" +
                    "where r.renting = true"
            ,
            countQuery = "SELECT count(*) from  customer c\n" +
                    "left join rent r on c.id = r.customer_id\n" +
                    "where r.renting = true")
    Page<CustomerEntity> findCustomerRenting(Pageable pageable);

    boolean existsByPhoneNumber(String phone);
}
