package top.hting.stock.api;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zzwen6
 * @date 2023/8/28 19:47
 */
@FeignClient(name = "xueQiuHeaderFeignApi", url = "https://xueqiu.com/")
public interface XueQiuHeaderFeignApi {

    /**
     * 行情 html
     * @return
     */
    @GetMapping(value = "/hq",produces = "text/html;charset=utf-8")
    Response getHqPage();


}
