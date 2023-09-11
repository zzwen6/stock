package top.hting.stock.schedule;

import com.alibaba.fastjson2.JSON;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.hting.stock.api.XueQiuFeignApi;
import top.hting.stock.api.XueQiuHeaderFeignApi;
import top.hting.stock.config.XueQiuConstant;
import top.hting.stock.dao.QuoteEntityRepository;
import top.hting.stock.dto.xueqiu.Quote;
import top.hting.stock.dto.xueqiu.XueQiuDetail;
import top.hting.stock.dto.xueqiu.XueQiuResult;
import top.hting.stock.entity.QuoteEntity;
import top.hting.stock.utils.DateUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author zzwen6
 * @date 2023/8/29 11:58
 */
@Service
@Slf4j
public class XueQiuJob {
    @Autowired
    private XueQiuService xueQiuService;

    /**
     * 拉取请求头 cookies
     */
    @Scheduled(cron = "0 */15 9-15 * * ?")
    @PostConstruct
    public void pullHeader() {
        xueQiuService.pullHeader();
    }

    /**
     * 拉取数据
     */
   // @Scheduled(fixedRate = 5000)
    public void pullData() {
        xueQiuService.pullData();
    }

    // 每天休市后的结果数据
    // @Scheduled(cron = "0 10 15 * * ?")
    // @Scheduled(cron = "0 10 15 * * ?")
    @Scheduled(fixedRate = 10000)
    public void pullResult() {
        xueQiuService.pullResult();
    }

}
