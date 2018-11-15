the actual caching store is not implemented by spring caching framework.

spring-cache: This is an abstract framework where Spring only provides the layer where other third party caching implementations can be easily plugged for storing data.

. Spring’s has the abstraction for the following list of cache implementations out of the box:
1.	JDK java.util.concurrent.ConcurrentMap based caches
2.	EhCache
3.	Gemfire Cache
4.	Guava Caches
5.	JSR 107 complaint caches


. For the ConcurrentMap we just need the Cache manager to be configured and the data stored in the in-memory,

@Caching
@Caching annotation used for grouping multiple annotations of the same type together when one annotation is not sufficient for the specifying the suitable condition. For example, you can put mutiple @CacheEvict ot @CachePut annotation inside @Caching to narrow down your conditions as you need.
The list of attributes supported in this annotation is shown below:
Attributes	Description
cacheable	This is array of @Cacheable annotation
evict	This is array of @CacheEvict annotation
put	This is array of @CachePut annotation


If you are enabling enabling caching in your spring applications, then you have to take care of the following two things:
1.	Caching declaration – Identify the methods that has to be cached and define the caching policy.
2.	Cache Configurations – Configure the cache manager where the backing data is stored and retrieved for the quick response.
3.	EnableCaching – Finally you have to enable the caching using Java configurations or the XML configurations.

@EnableCaching
public class Application {
If you are using XML configurations, then it looks like this:
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:cache="http://www.springframework.org/schema/cache" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/cache http://www.springframework.org/schema/cache/spring-cache.xsd">

        <cache:annotation-driven />

</beans>

We can see cache size in metrics endpoint provided by actuator library
/metrics
cache.ticketsCache.size=1

To disable caching, set spring.cache.type=none


Can specify multiple cache names:
spring.cache.cache-names=ticketsCache, personCache



JCache (JSR – 107) Support
Since spring framework 4.1, spring’s caching abstraction completely supports the JCache specification and you can use JCache annotations without any special configurations. Here is the table that compares the list of annotations in Spring and JCache specification.
Spring	JCache (JSR-107)	Remarks
@Cacheable	@CacheResult	
@CachePut	@CachePut	 There is no change in the annotation name
@CacheEvict	@CacheRemove	 @CacheRemove evicts conditionaly when
there is exception throw from the method.
@CacheEvict(allEntries=true)	@CacheRemoveAll	 JCache adds another annotation for
removing all the cache entries
@CacheConfig	@CacheDefaults	 Both the annotations work similar
The JCache is located under the package org.springframework.cache.jcache. You can declare the JCache as like this:
<bean id="cacheManager" class="org.springframework.cache.jcache.JCacheCacheManager" p:cache-manager-ref="jCacheManager"/>

<!-- JSR-107 cache manager setup -->
<bean id="jCacheManager" .../>
If you are using Java based configuration, please add the below lines of code to your configuration class:
@Bean
public CacheManager jCacheManager() {
   return new JCacheCacheManager();
}
