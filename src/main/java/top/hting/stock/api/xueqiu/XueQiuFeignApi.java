package top.hting.stock.api.xueqiu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.hting.stock.dto.xueqiu.XueQiuDetail;
import top.hting.stock.dto.xueqiu.XueQiuResult;
import top.hting.stock.dto.xueqiu.XueQiuStockList;

@FeignClient(name = "xueQiuFeignApi", url = "https://stock.xueqiu.com/v5", configuration = XueQiuFeignConfig.class)
public interface XueQiuFeignApi {

    /**
     * 获取股票当当前的详情数据
     * @param symbol 股票代码 带有SH\SZ 前缀
     * @param extend
     * @return
     */
    @GetMapping("/stock/quote.json")
    public XueQiuResult<XueQiuDetail> getDetail(@RequestParam String symbol,
                                                @RequestParam(required = false, defaultValue = "detail") String extend);

    /**
     * 获取当前某个市场的列表
     * @param page
     * @param size
     * @param order
     * @param orderBy
     * @param market
     * @param type
     * @return
     */
    @GetMapping("/stock/screener/quote/list.json")
    public XueQiuResult<XueQiuStockList> getStockList(@RequestParam Integer page,
                                                      @RequestParam(defaultValue = "90") Integer size,
                                                      @RequestParam(defaultValue = "asc") String order,
                                                      @RequestParam(defaultValue = "market_capital",name = "order_by") String orderBy, // 按市值排序
                                                      @RequestParam(defaultValue = "CN") String market,
                                                      @RequestParam(defaultValue = "sha")  String type);


}
