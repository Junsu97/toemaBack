package junsu.personal.persistance;

public interface IMyRedisMapper {
    int saveAuth(String redisKey, String authNumber) throws Exception;
    String getAuth(String redisKey) throws Exception;
}
