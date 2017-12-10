//package org.unistacks.utils;
//
//import com.google.common.cache.CacheBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurer;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.guava.GuavaCache;
//import org.springframework.cache.interceptor.CacheErrorHandler;
//import org.springframework.cache.interceptor.CacheResolver;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.cache.support.SimpleCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//import static com.unistack.tamboo.common.Const.TAMBOO_COLLECT;
//import static com.unistack.tamboo.common.Const.TAMBOO_MACHINE;
//
///**
// * Created by Gyges on 2017/11/17
// *
// * @author Gyges Zean
// */
//@Configuration
//@EnableCaching
//public class CacheConfig implements CachingConfigurer {
//
//    private final Logger logger = LoggerFactory.getLogger(CacheConfig.class);
//
//    /**
//     * 最大存储大小
//     */
//    @Value("${simple.cache.maxSize}")
//    private long cacheMaxSize;
//    /**
//     * 缓存过期时间
//     */
//    @Value("${simple.cache.expireTime}")
//    private long expireTime;
//
//
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        logger.info("Initializing simple Guava Cache manager");
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
////      用于采集器缓存
//        GuavaCache collectCache = new GuavaCache(TAMBOO_COLLECT, CacheBuilder.newBuilder()
//                .maximumSize(cacheMaxSize)
//                .expireAfterWrite(expireTime,TimeUnit.SECONDS)
//                .build());
//
////       用于机器缓存
//        GuavaCache machineCache = new GuavaCache(TAMBOO_MACHINE, CacheBuilder.newBuilder()
//                .maximumSize(cacheMaxSize)
//                .expireAfterWrite(expireTime,TimeUnit.SECONDS)
//                .build());
//
//        cacheManager.setCaches(Arrays.asList(collectCache, machineCache));
//
//        return cacheManager;
//    }
//
//
//
//
//    @Override
//    public CacheResolver cacheResolver() {
//        return null;
//    }
//
//    @Override
//    public KeyGenerator keyGenerator() {
//        return null;
//    }
//
//    @Override
//    public CacheErrorHandler errorHandler() {
//        return null;
//    }
//}
