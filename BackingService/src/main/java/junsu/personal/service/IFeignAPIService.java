package junsu.personal.service;

import feign.RequestLine;
import junsu.personal.dto.object.ApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jejuDataApi", url = "https://open.jejudatahub.net/api/proxy/")
public interface IFeignAPIService {

    @GetMapping("/9abD88tb7b8t9b97D9t8D9bttat79Daa")
    ApiDTO getData(@RequestParam("grade") String grade);
}
