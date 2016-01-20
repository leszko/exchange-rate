package com.summercoding.zooplus;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

    public static final String HISTORICAL_EXCHANGE_CACHE_NAME = "historicalExchangeRates";
    private static final String MEMORY_STORE_EVICTION_POLICY = "LRU";

    @Value("${cache.ttl}")
    private int cacheTtl;

    @Value("${cache.size}")
    private int cacheSize;

    @Bean(destroyMethod = "shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName(HISTORICAL_EXCHANGE_CACHE_NAME);
        cacheConfiguration.setTimeToLiveSeconds(cacheTtl);
        cacheConfiguration.setMemoryStoreEvictionPolicy(MEMORY_STORE_EVICTION_POLICY);
        cacheConfiguration.setMaxEntriesLocalHeap(cacheSize);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfiguration);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
