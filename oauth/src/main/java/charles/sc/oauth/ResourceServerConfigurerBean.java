package charles.sc.oauth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Created by Charles on 2016/12/27.
 */
public class ResourceServerConfigurerBean implements ResourceServerConfigurer {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/pb/**").permitAll()  // pb 允许
                .antMatchers("/pt/**").access("#oauth2.hasScope('all')")    // pt oauth2验证，scope=all
                .anyRequest().permitAll();    // 其他请求允许
    }
}
