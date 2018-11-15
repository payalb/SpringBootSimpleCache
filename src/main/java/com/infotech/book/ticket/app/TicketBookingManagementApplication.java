package com.infotech.book.ticket.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class TicketBookingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingManagementApplication.class, args);
	}
	
	/*If we don't configure a cache-manager, spring will auto-configure it for us that uses concurrent maps in memory
	 *Not recommended for production use.
	 *Can configure default cache name by spring.cache.cache-names in application.properties
	 */	
	@Bean
	public CacheManager cacheManager(){
		return new ConcurrentMapCacheManager("ticketsCache");
	}
}
