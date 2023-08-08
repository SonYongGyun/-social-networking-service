package kr.co.mz.sns.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static kr.co.mz.sns.config.security.SecurityConstants.JWT_EXPIRATION;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveToCache(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, TimeUnit.SECONDS.toMillis(JWT_EXPIRATION));
    }

    public Object getFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteFromCache(String key) {
        redisTemplate.delete(key);
    }
}
