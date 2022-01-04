package com.jt.consumer.service.factory;

import com.jt.consumer.service.RemoteProviderService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProviderFallbackFactory implements FallbackFactory<RemoteProviderService> {
    @Override
    public RemoteProviderService create(Throwable throwable) {
        return new RemoteProviderService() {
            @Override
            public String echoMessage(String string) {
                return "服务器寄了";
            }
        };
    }
}
