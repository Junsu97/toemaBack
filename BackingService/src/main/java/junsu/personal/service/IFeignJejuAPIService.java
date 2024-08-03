package junsu.personal.service;

import feign.Param;
import feign.RequestLine;
import junsu.personal.dto.object.ApiDTO;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "jejuDataApi", url = "https://open.jejudatahub.net/api/proxy/")
public interface IFeignJejuAPIService {

    @RequestLine("GET /9abD88tb7b8t9b97D9t8D9bttat79Daa/{apiKey}?grade={grade}")
    ApiDTO getData(@Param("apiKey") String apiKey, @Param("grade") String grade);


}
