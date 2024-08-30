package com.personalize.gateway.filter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

//@Configuration
public class AuthorizePostFilter {
    @Bean
    public GlobalFilter postGlobalFilter(){
        return ((exchange, chain) -> {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(()->{
//                        UserHolder.removeUser();
            }));
        });
    }

}