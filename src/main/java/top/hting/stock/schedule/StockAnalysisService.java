package top.hting.stock.schedule;

import cn.hutool.cache.Cache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.hting.stock.dao.XueQiuEntityRepository;
import top.hting.stock.dao.XueQiuRealTimeEntityRepository;
import top.hting.stock.dao.XueQiuStockEntityRepository;
import top.hting.stock.entity.XueQiuEntity;
import top.hting.stock.entity.XueQiuRealTimeEntity;
import top.hting.stock.entity.XueQiuStockEntity;

import java.util.List;

/**
 * @author zzwen6
 * @date 2024/6/8 22:04
 */
@Service
@Slf4j
public class StockAnalysisService {

    @Value("${weixin.webhook}")
    private String robotWebhook;

    @Autowired
    XueQiuEntityRepository xueQiuEntityRepository;
    @Autowired
    XueQiuRealTimeEntityRepository xueQiuRealTimeEntityRepository;
    @Autowired
    XueQiuStockEntityRepository xueQiuStockEntityRepository;
    private static final Cache<String, Integer> CACHE = new TimedCache<>(3600000); // 一小时的缓存

    /**
     * 以天分析数据
     * 以三天、五天、十天分析，对比查看升降多少
     * 规则警戒线：TODO 这个待定,用表做为配置触发升降多少，然后强烈提醒
     *
     * 此定时器在：@pullDataEveryDay 之后执行
     */
    public void analyzeStockByDay() {

        Pageable pageable = PageRequest.of(0, 10);
        List<XueQiuStockEntity> list = xueQiuStockEntityRepository.findAll();
        for (XueQiuStockEntity stock : list) {
            String symbol = stock.getId();
            List<XueQiuEntity> details = xueQiuEntityRepository.findBySymbolOrderByDatesDesc(symbol, pageable);
            this.processByDay(details, stock);
        }
    }


    /**
     * 实时分析当前股份，对比升降情况，触发报警
     * 取最新的即可
     * TODO 拿天的数据比对升降情况
     *
     */
    public void analyzeStockByRealTime() {
        List<XueQiuStockEntity> list = xueQiuStockEntityRepository.findAll();
        for (XueQiuStockEntity xueQiuStockEntity : list) {
            XueQiuRealTimeEntity data = xueQiuRealTimeEntityRepository.findFirstByCodeOrderByTimestampeDesc(xueQiuStockEntity.getCode());
            double v = data.getPercent().abs().doubleValue();
            if (v > xueQiuStockEntity.getDayPercent()) {
                log.info("阈值触达通知: {}, {} ",xueQiuStockEntity.getCode(), data.getPercent() );
                this.send(xueQiuStockEntity.getCode(), data);
            }
        }
    }

    private void processByDay(List<XueQiuEntity> details, XueQiuStockEntity stock) {

    }

    private void send(String code, XueQiuRealTimeEntity data) {

        Integer count = CACHE.get(code);
        if (count == null) {
            CACHE.put(code, 1);
        } else {
            if (count < 5) {
                CACHE.put(code, count + 1);
            } else {
                log.info("提醒次数过多 不再提醒: {} ", code);
                // 提醒次数过多，不再提醒
                return;
            }
        }


        String message = " {\n" +
                "    \"msgtype\": \"markdown\",\n" +
                "    \"markdown\": {\n" +
                "        \"content\": \"${code} 告警\n" +
                "    比例: <font color=\\\"${color}\\\">${percent}</font>\n" +
                "    当前: <font color=\\\"${color}\\\">${current}</font>\n" +
                "    涨跌: <font color=\\\"${color}\\\">${chg}</font>\n" +
                "    今开: <font color=\\\"comment\\\">${open}</font>\"\n" +
                "    }\n" +
                "} ";

        message = StrUtil.replace(message, "${color}", data.getPercent().doubleValue() >= 0?"red":"green");
        message = StrUtil.replace(message, "${code}", code);
        message = StrUtil.replace(message, "${chg}", data.getChg().toString());
        message = StrUtil.replace(message, "${percent}", data.getPercent().toString());
        message = StrUtil.replace(message, "${current}", data.getCurrent().toString());
        message = StrUtil.replace(message, "${open}", data.getOpen().toString());
        HttpUtil.post(robotWebhook, message);


    }
}
