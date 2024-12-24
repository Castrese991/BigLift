package com.big_lift.palestra.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.big_lift.palestra.security.FeignConfig;


@FeignClient(name = "customer-service", url = "${customer-service.url}", configuration = FeignConfig.class)
public interface CustomerClient
{
	@PutMapping("/{customerId}/assign-trainer/{trainerId}")
	CustomerResponse assignTrainerToCustomer(@PathVariable("customerId") Long customerId, @PathVariable("trainerId") Long trainerId);

	@GetMapping("/getCustomer/{id}")
	Optional<CustomerResponse> getCustomerById(@PathVariable("id") Long customerId);
}
