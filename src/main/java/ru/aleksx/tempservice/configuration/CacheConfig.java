package ru.aleksx.tempservice.configuration;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aleksx.tempservice.controller.dto.TemperatureDataDto;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;
import static org.ehcache.config.units.MemoryUnit.MB;

@EnableCaching
@Configuration
public class CacheConfig {


    @Bean
    public CacheManager jCacheManagerCustomizer() {

        CacheConfiguration<String, List> tempListCacheConfiguration =
                newCacheConfigurationBuilder(String.class, List.class, heap(100).offheap(20, MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(1L)))
                        .build();

        CacheConfiguration<String, TemperatureDataDto> temperatureCacheConfiguration =
                newCacheConfigurationBuilder(String.class, TemperatureDataDto.class, heap(100).offheap(20, MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(1L)))
                        .build();


        Map<String, CacheConfiguration<?, ?>> caches = new HashMap<>();
        caches.put("temperatureDataList", tempListCacheConfiguration);
        caches.put("temperatureData",temperatureCacheConfiguration);


        EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
        org.ehcache.config.Configuration configuration = new DefaultConfiguration(caches, provider.getDefaultClassLoader());
        return provider.getCacheManager(provider.getDefaultURI(), configuration);

    }
}
