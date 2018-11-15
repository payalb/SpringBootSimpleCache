package com.infotech.book.ticket.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.infotech.book.ticket.app.dao.TicketBookingDao;
import com.infotech.book.ticket.app.entities.Ticket;

@Service
/*You can annotate @CacheConfig at the class level to avoid repeated mentioning in each method. For example, in the class level you can provide the cache name and in the method you just annotate with @Cacheable annotation.*/
@CacheConfig(cacheNames="ticketsCache")
public class TicketBookingService {

	@Autowired
	private TicketBookingDao ticketBookingDao;
	/*If you annotate a method with @Cacheable, if multiple requests are received by the application, then this annotation will not execute the method multiple times, instead it will send the result from the cached storage.
	 * cacheManager	The bean name of the cache manager
	cacheNames	The list of cache store names where the method cache has to be stored. This should be any array of strings.
	cacheResolver	The name of the custom cache resolver condition	This is Spring Expression Language (SPeL) for the conditional caching for the method key	Spring Expression Language for computing the key dynamically
	keyGenerator	The bean name of the custom key generator to use unless	Spring Expression Language for bypassing caching for specific scenarios.
	value	It is a cache name to store the caches
	
	If we don't specify a key, method argument is taken as the key.Method return value is taken as a value in the cache
*/
	@Cacheable(key="#ticketId", condition="#result!= null")
	public Ticket getTicketById(Integer ticketId) {
		return ticketBookingDao.findOne(ticketId);
	}
	
	/*It is used for removing a single cache or clearing the entire cache from the cache storage.
	 * If you set the allEntries=true , then the entire cache will be cleared.
	 * beforeInvocation	This attribute indicates whether the eviction has to be done before the method invocation.
*/
	//@CacheEvict(value="ticketsCache",key="#ticketId")
	@CacheEvict(key="#ticketId")
	public void deleteTicket(Integer ticketId) {
		ticketBookingDao.delete(ticketId);
	}
	
	/*@CachePut annotation helps for updating the cache with the latest execution without stopping the method execution.
	 * Method executed every time and updates the cache storage.*/
	@CachePut(key="#ticketId", unless="#result==null")
	public Ticket updateTicket(Integer ticketId, String newEmail) {
		Ticket ticketFromDb = ticketBookingDao.findOne(ticketId);
		ticketFromDb.setEmail(newEmail);
		Ticket upadedTicket = ticketBookingDao.save(ticketFromDb);
		return upadedTicket;
	}
	
	//For methods with no arguments/ static key:
	
	public static final String TICKET_KEY= "tickets";
	
	@Cacheable(key="#root.target.TICKET_KEY")
	public Iterable<Ticket> getTickets() {
		return ticketBookingDao.findAll();
	}
	
	@Cacheable(key="#result.ticketId")
	public Ticket insertTicket(Ticket ticket) {
		return ticketBookingDao.save(ticket);
	}
}
