package com.agatarauzer.myBooks.statistics;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/admin/statictics")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminStatisticsController {
	
	private final AdminStatisticsService adminStatisticsService;
	
	@GetMapping("/registred")
	public Map<String, Long> getUsersRegistredByMonth() {
		return adminStatisticsService.findUsersRegisteredByMonth();
	}
	
	@GetMapping("/registred/role")
	public Map<String, Long> getUsersRegistredByMonthByRole(@PathVariable String role) {
		return adminStatisticsService.findUsersRegistredByMonthByRole(role);
	}
	
	@GetMapping("/avg")
	public Double getAvgNumberOfBooksForUsers() {
		return adminStatisticsService.calculateAvgNumberOfBooksForUsers();
	}
}


