package charles.sc.provider.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Charles on 2016/8/16.
 */
@RefreshScope
@Service
@RequestMapping("/foo")
@ResponseBody
public class FooService {
    private Log log = LogFactory.getLog(FooService.class);

    @Value("${foo:foo}")
    private String foo;

    @HystrixCommand(fallbackMethod = "fallbackBar")
    @RequestMapping(method = RequestMethod.GET)
    public String bar() {
        log.info("FooService.bar");
//        if(System.currentTimeMillis() % 2 == 0){
//            throw new RuntimeException();
//        }
        return "bar " + foo +System.currentTimeMillis();
    }

    public String fallbackBar() {
        log.info("FooService.fallbackBar");
        return "fallbackBar" + foo +System.currentTimeMillis();
    }
}
