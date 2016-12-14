package charles.sc.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Charles on 2016/12/7.
 */
@FeignClient(value = "provider")
@RequestMapping("/foo")
public interface FooService {
    @RequestMapping(method = RequestMethod.GET)
    public String bar();
}
