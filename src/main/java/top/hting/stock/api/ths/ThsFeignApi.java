package top.hting.stock.api.ths;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 同花顺 页面API
 */
@FeignClient(name = "thsFeignApi", url = "https://q.10jqka.com.cn/")
public interface ThsFeignApi {

    /**
     * 同花顺， 行业板块
     * @return
     */
    @GetMapping(value = "/thshy",produces = "text/html;charset=charset=gbk")
    Response getThshyPage();

    /**
     * 同花顺， 概念板块
     * @return
     */
    @GetMapping(value = "/gn",produces = "text/html;charset=charset=gbk")
    Response getGnPage();


}
