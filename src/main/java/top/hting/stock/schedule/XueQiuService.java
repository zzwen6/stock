package top.hting.stock.schedule;

import com.alibaba.fastjson2.JSON;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hting.stock.api.xueqiu.XueQiuFeignApi;
import top.hting.stock.api.xueqiu.XueQiuHeaderFeignApi;
import top.hting.stock.config.XueQiuConstant;
import top.hting.stock.dao.QuoteEntityRepository;
import top.hting.stock.dao.SymbolConfigRepository;
import top.hting.stock.dto.xueqiu.Quote;
import top.hting.stock.dto.xueqiu.XueQiuDetail;
import top.hting.stock.dto.xueqiu.XueQiuResult;
import top.hting.stock.entity.QuoteEntity;
import top.hting.stock.entity.SymbolConfig;
import top.hting.stock.utils.DateUtils;

import java.util.*;

@Service
@Slf4j
public class XueQiuService {

    @Autowired
    XueQiuHeaderFeignApi xueQiuHeaderFeignApi;
    @Autowired
    XueQiuFeignApi xueQiuFeignApi;

    @Autowired
    QuoteEntityRepository quoteEntityRepository;
    @Autowired
    SymbolConfigRepository symbolConfigRepository;

    /**
     * 拉取请求头 cookies
     */
    public void pullHeader() {
        log.info("Pulling header...");
        Response response = xueQiuHeaderFeignApi.getHqPage();
        Map<String, Collection<String>> headers = response.headers();
        Collection<String> collection = headers.get("set-cookie");
        if (collection != null && !collection.isEmpty()) {
            List<String> list = new ArrayList<>(collection);
            XueQiuConstant.HEADER_COOKIES = String.join(";", list);
        }
        log.info("Header pulled.");
    }

    /**
     * 拉取数据
     */
    public void pullData() {
        log.info("Pulling data...");
        XueQiuResult<XueQiuDetail> detailResult = xueQiuFeignApi.getDetail("SH601919", "detail");
        Quote quote = detailResult.getData().getQuote();
        log.info("Data pulled. {}", JSON.toJSONString(quote));
    }

    // 每天休市后的结果数据
    public void pullResult() {
        log.info("Pulling result...");
        // 获取需要关注的代码
        List<SymbolConfig> configs = symbolConfigRepository.findAll();
        for (SymbolConfig config : configs) {
            XueQiuResult<XueQiuDetail> detailResult = xueQiuFeignApi.getDetail(config.getSymbol(), "detail");
            Quote quote = detailResult.getData().getQuote();
            // 存入数据库中
            QuoteEntity entity = new QuoteEntity();
            BeanUtils.copyProperties(quote, entity);
            String formatDate = DateUtils.format(quote.getTime(), "yyyyMMdd");
            String id = quote.getSymbol() + "." + formatDate;
            if (!quoteEntityRepository.findById(id).isPresent()) {
                entity.setId(id);
                entity.setStockDate(Integer.valueOf(formatDate));
                quoteEntityRepository.save(entity);
            }
        }
        log.info("Result pulled.");
    }

}
