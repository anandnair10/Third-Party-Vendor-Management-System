package com.capstone.Third_Party_Vendor_Management_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories("com.capstone.Third_Party_Vendor_Management_System.repository")


@SpringBootApplication
public class ThirdPartyVendorManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirdPartyVendorManagementSystemApplication.class, args);
	}

}
