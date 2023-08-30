package top.hting.stock.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.hting.stock.dto.xueqiu.XueQiuDetail;
import top.hting.stock.dto.xueqiu.XueQiuResult;

@FeignClient(name = "xueQiuFeignApi", url = "https://stock.xueqiu.com/v5", configuration = XueQiuFeignConfig.class)
public interface XueQiuFeignApi {

    @GetMapping("/stock/quote.json")
    public XueQiuResult<XueQiuDetail> getDetail(@RequestParam String symbol,
                                                @RequestParam(required = false, defaultValue = "detail") String extend);

}
