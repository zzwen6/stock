package top.hting.stock.api.xueqiu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import top.hting.stock.config.XueQiuConstant;

@Configuration
public class XueQiuFeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        // 拿取请求头

        return template -> {
            template.header("Cookie",
                    XueQiuConstant.HEADER_COOKIES);
            // 添加其他请求头
            template.header("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        };
    }
}
