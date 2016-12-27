package charles.sc.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Charles on 2016/12/27.
 */
public class AuthorizationServerConfigurerBean implements AuthorizationServerConfigurer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
    }
}
