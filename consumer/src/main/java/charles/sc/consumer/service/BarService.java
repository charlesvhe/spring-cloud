package charles.sc.consumer.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Charles on 2016/12/7.
 */
@Service
@RequestMapping("/bar")
@ResponseBody
public class BarService {
    private Log log = LogFactory.getLog(BarService.class);
    @Autowired
    private FooService fooService;

    @RequestMapping(method = RequestMethod.GET)
    public String foo() {
        log.info("BarService.foo");
        return fooService.bar();
    }
}
