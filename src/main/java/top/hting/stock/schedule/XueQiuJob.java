package top.hting.stock.schedule;

import com.alibaba.fastjson2.JSON;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.hting.stock.api.XueQiuFeignApi;
import top.hting.stock.api.XueQiuHeaderFeignApi;
import top.hting.stock.config.XueQiuConstant;
import top.hting.stock.dto.xueqiu.Quote;
import top.hting.stock.dto.xueqiu.XueQiuDetail;
import top.hting.stock.dto.xueqiu.XueQiuResult;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zzwen6
 * @date 2023/8/29 11:58
 */
@Service
public class XueQiuJob {

    @Autowired
    XueQiuHeaderFeignApi xueQiuHeaderFeignApi;
    @Autowired
    XueQiuFeignApi xueQiuFeignApi;

    /**
     * 拉取请求头 cookies
     */
    @Scheduled(cron = "0 */15 9-15 * * ?")
    @PostConstruct
    public void pullHeader() {
        Response response = xueQiuHeaderFeignApi.getHqPage();
        Map<String, Collection<String>> headers = response.headers();
        Collection<String> collection = headers.get("set-cookie");
        if (collection != null && !collection.isEmpty()) {
            List<String> list = new ArrayList<>(collection);
            XueQiuConstant.HEADER_COOKIES = String.join(";", list);
        }
    }

    /**
     * 拉取数据
     */
    @Scheduled(fixedRate = 5000)
    public void pullData() {
        XueQiuResult<XueQiuDetail> detailResult = xueQiuFeignApi.getDetail("SH601919", "detail");
        Quote quote = detailResult.getData().getQuote();
        System.out.println(JSON.toJSONString(quote));
    }

}
