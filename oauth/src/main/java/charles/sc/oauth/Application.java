package charles.sc.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Charles on 2016/12/6.
 */
@SpringBootApplication
@EnableAuthorizationServer
public class Application implements AuthorizationServerConfigurer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return jdbcTemplate.queryForObject("SELECT * FROM user_details WHERE username=?", new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new User(resultSet.getString("username"),
                                resultSet.getString("password"),
                                AuthorityUtils.commaSeparatedStringToAuthorityList(resultSet.getString("authority")));
                    }
                }, username);
            }
        };
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new ClientDetailsService() {
            @Override
            public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
                return jdbcTemplate.queryForObject("SELECT * FROM client_details WHERE client_id=?", new RowMapper<BaseClientDetails>() {
                    @Override
                    public BaseClientDetails mapRow(ResultSet resultSet, int i) throws SQLException {
                        BaseClientDetails clientDetails = new BaseClientDetails(resultSet.getString("client_id"),
                                null,
                                resultSet.getString("scope"),
                                resultSet.getString("grant_types"),
                                null,
                                resultSet.getString("redirect_uri"));
                        clientDetails.setClientSecret(resultSet.getString("client_secret"));
                        return clientDetails;
                    }
                }, clientId);
            }
        });
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {  // 允许跨域
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "DELETE", "PUT", "GET", "OPTIONS");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
