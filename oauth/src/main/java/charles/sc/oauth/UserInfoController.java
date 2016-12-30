package charles.sc.oauth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Charles on 2016/12/30.
 */
@RestController
public class UserInfoController {
    @RequestMapping("/pb/resource")
    public String pbresource() {
        return "Hello World pbresource! " + System.currentTimeMillis();
    }

    @RequestMapping("/pt/resource")
    public String ptresource() {
        return "Hello World ptresource! " + System.currentTimeMillis();
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
