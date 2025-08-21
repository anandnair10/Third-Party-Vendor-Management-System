package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.dto.RatingDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.repository.EmployeeRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.RatingRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.RatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private Rating rating;
    private Employee employee;
    private Vendor vendor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setUsername("empuser");

        vendor = new Vendor();
        vendor.setId(1L);
        vendor.setBusinessName("VendorX");

        rating = new Rating();
        rating.setRatingValue(4);
        rating.setFeedback("Good service");
        rating.setRatingDate(LocalDate.now());
        rating.setEmployee(employee);
        rating.setVendor(vendor);
    }

    @Test
    void testAddRating() {
        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating saved = ratingService.addRating(rating);

        assertEquals(rating, saved);
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testGetRatingsForVendor() {
        List<Rating> ratings = List.of(rating);
        when(ratingRepository.findByVendorId(1L)).thenReturn(ratings);

        List<RatingDTO> result = ratingService.getRatingsForVendor(1L);

        assertEquals(1, result.size());
        assertEquals(rating.getRatingValue(), result.get(0).getRatingValue());
        assertEquals(rating.getFeedback(), result.get(0).getFeedback());
    }

    @Test
    void testGetRatingsByEmployee() {
        List<Rating> ratings = List.of(rating);
        when(ratingRepository.findByEmployeeId(1L)).thenReturn(ratings);

        List<Rating> result = ratingService.getRatingsByEmployee(1L);

        assertEquals(1, result.size());
        assertEquals(rating, result.get(0));
    }

    @Test
    void testUpdateRating_Success() {
        Rating updated = new Rating();
        updated.setRatingValue(5);
        updated.setFeedback("Excellent service");
        updated.setRatingDate(LocalDate.now());
        updated.setEmployee(employee);
        updated.setVendor(vendor);

        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(updated);

        Rating result = ratingService.updateRating(1L, updated);

        assertEquals(5, result.getRatingValue());
        assertEquals("Excellent service", result.getFeedback());
    }

    @Test
    void testUpdateRating_NotFound() {
        Rating updated = new Rating();
        when(ratingRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                ratingService.updateRating(1L, updated));

        assertEquals("Rating not found with ID: 1", exception.getMessage());
    }
}

