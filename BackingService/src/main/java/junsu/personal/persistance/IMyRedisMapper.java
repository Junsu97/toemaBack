package junsu.personal.persistance;

import junsu.personal.dto.object.CrawlingDTO;

import java.util.List;

public interface IMyRedisMapper {
    int saveAuth(String redisKey, String authNumber) throws Exception;
    String getAuth(String redisKey) throws Exception;

    int saveCrawling(String redisKey, List<CrawlingDTO> list) throws Exception;
    List<CrawlingDTO> getCrawling(String redisKey) throws Exception;
}
