package charles.sc.provider.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Charles on 2016/8/16.
 */
@Service
@RequestMapping("/foo")
@ResponseBody
public class FooService {
    private Log log = LogFactory.getLog(FooService.class);

    @RequestMapping(method = RequestMethod.GET)
    public String bar() {
        log.info("FooService.bar");
        return "bar " + System.currentTimeMillis();
    }
}
