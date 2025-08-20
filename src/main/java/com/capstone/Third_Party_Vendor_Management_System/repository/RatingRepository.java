package com.capstone.Third_Party_Vendor_Management_System.repository;

import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByVendorId(Long id);
    List<Rating> findByEmployeeId(Long id);

    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.vendor.id =:vendorid")
    Double findAverageRatingByVendorId(@Param("vendorid")Long vendorId);
}
