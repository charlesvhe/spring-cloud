##使用consul作为 DNS\注册中心\配置中心\Spring Cloud Bus

    consul agent -server -bootstrap -advertise=10.2.107.218 -data-dir=./data -ui-dir=./web_ui

###使用docker 集群

docker swarm overlay 网络

    docker network create --driver overlay --subnet 172.20.0.0/24 mc
    
下面命令只能通过控制台执行，无法通过portainer webUI执行

    docker service create \
        --network=mc \
        --name=consul \
        -e CONSUL_BIND_INTERFACE=eth2 \
        --mode global \
        -p 8500:8500 \
        10.3.32.86:5000/consul agent -server -ui -client=0.0.0.0 \
        -bootstrap-expect 1 \
        -retry-join 172.20.0.3

###使用http2
@Bean
    public EmbeddedServletContainerCustomizer tomcatCustomizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof TomcatEmbeddedServletContainerFactory) {
                    ((TomcatEmbeddedServletContainerFactory) container)
                            .addConnectorCustomizers(new TomcatConnectorCustomizer() {
                                @Override
                                public void customize(Connector connector) {
                                    connector.addUpgradeProtocol(new Http2Protocol());
                                }

                            });
                }
            }

        };
    }

验证：curl --http2 -i http://localhost:8100/foo

