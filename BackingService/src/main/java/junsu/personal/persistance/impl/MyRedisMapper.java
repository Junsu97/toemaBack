package junsu.personal.persistance.impl;

import junsu.personal.persistance.IMyRedisMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyRedisMapper implements IMyRedisMapper {
    private final RedisTemplate<String, Object> redisDB;
    private void deleteRedisKey(String redisKey){
        if(redisDB.hasKey(redisKey)){
            redisDB.delete(redisKey);

            log.info("삭제 성공!");
        }
    }
    @Override
    public int saveAuth(String redisKey, String authNumber) throws Exception {
        log.info(this.getClass().getName() + ".saveAuth Start!!!");

        int res;
        this.deleteRedisKey(redisKey);
        redisDB.opsForValue().set(redisKey, String.valueOf(authNumber));
        redisDB.expire(redisKey, 5, TimeUnit.MINUTES);
        res = 1;
        log.info(this.getClass().getName() + ".saveAuth End!!!");
        return res;
    }

    @Override
    public String getAuth(String redisKey) throws Exception {
        log.info(this.getClass().getName() + ".getAuth Start!!!!");
        log.info("String redisKey : " + redisKey);
        String res = "";
        if(redisDB.hasKey(redisKey)){
            res = (String) redisDB.opsForValue().get(redisKey);

            log.info("res : " + res);
        }
        log.info(this.getClass().getName() + ".getAuth End!!!!");
        deleteRedisKey(redisKey);
        return res;
    }
}
