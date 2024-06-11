package top.hting.stock.schedule;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.util.DateUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hting.stock.api.XueQiuFeignApi;
import top.hting.stock.api.XueQiuHeaderFeignApi;
import top.hting.stock.config.XueQiuConstant;
import top.hting.stock.dao.XueQiuEntityRepository;
import top.hting.stock.dao.XueQiuRealTimeEntityRepository;
import top.hting.stock.dao.XueQiuStockEntityRepository;
import top.hting.stock.dto.xueqiu.Quote;
import top.hting.stock.dto.xueqiu.XueQiuDetail;
import top.hting.stock.dto.xueqiu.XueQiuResult;
import top.hting.stock.entity.XueQiuEntity;
import top.hting.stock.entity.XueQiuRealTimeEntity;
import top.hting.stock.entity.XueQiuStockEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    @Autowired
    XueQiuEntityRepository xueQiuEntityRepository;
    @Autowired
    XueQiuRealTimeEntityRepository xueQiuRealTimeEntityRepository;
    @Autowired
    XueQiuStockEntityRepository xueQiuStockEntityRepository;

    /**
     * 拉取数据-每天一次，3点之后
     */
    @XxlJob("pullDataEveryDay")
    public ReturnT<String> pullData() {
        // 每次拉取请求头，防止失效
        this.pullHeader();

        List<XueQiuStockEntity> list = xueQiuStockEntityRepository.findAll();
        for (XueQiuStockEntity stock : list) {
            String symbol = stock.getId();
            this.getStockDataByDay(symbol);
        }

        return ReturnT.SUCCESS;
    }


    /**
     * 每分钟拉取一次
     * 分析
     * @return
     */
    @XxlJob("pullDataRealTime")
    public ReturnT<String> pullDataRealTime() {
        // String symbol = "SH601919";
        // 每次拉取请求头，防止失效
        this.pullHeader();
        List<XueQiuStockEntity> list = xueQiuStockEntityRepository.findAll();
        for (XueQiuStockEntity stock : list) {
            String symbol = stock.getId();
            this.getStockRealTimeData(symbol);
        }
        return ReturnT.SUCCESS;
    }

    private void getStockDataByDay(String symbol) {
        XueQiuResult<XueQiuDetail> detailResult = xueQiuFeignApi.getDetail(symbol, "detail");
        Quote quote = detailResult.getData().getQuote();

        XueQiuEntity entity = new XueQiuEntity();
        entity.setSymbol(symbol);
        entity.setCode(quote.getCode());
        String date = DateUtils.format(new Date(quote.getTime()), "yyyyMMdd");

        XueQiuEntity db = xueQiuEntityRepository.findById(entity.getCode() + "-" + date).orElse(null);
        if (db == null) {
            entity.setId(entity.getCode() + "-" + date);
            entity.setDates(Integer.parseInt(date));
            entity.setTimes(quote.getTime());
            entity.setName(quote.getName());
            entity.setTimestampe(quote.getTimestamp());
            entity.setChg(quote.getChg());
            entity.setCurrent(quote.getCurrent());
            entity.setHigh(quote.getHigh());
            entity.setLow(quote.getLow());
            entity.setPercent(quote.getPercent());
            entity.setHigh52w(quote.getHigh52w());
            entity.setLow52w(quote.getLow52w());
            entity.setAmount(quote.getAmount());
            entity.setAmplitude(quote.getAmplitude());
            entity.setCurrency(quote.getCurrency());
            entity.setNavps(quote.getNavps());
            entity.setType(quote.getType());
            entity.setVolume(quote.getVolume());
            entity.setDividend(quote.getDividend());
            entity.setEps(quote.getEps());
            entity.setExchange(quote.getExchange());
            entity.setPb(quote.getPb());
            entity.setProfit(quote.getProfit());
            entity.setStatus(quote.getStatus());
            entity.setOpen(quote.getOpen());
            xueQiuEntityRepository.save(entity);
        }
    }

    private void getStockRealTimeData(String symbol) {
        XueQiuResult<XueQiuDetail> detailResult = xueQiuFeignApi.getDetail(symbol, "detail");
        Quote quote = detailResult.getData().getQuote();

        XueQiuRealTimeEntity entity = new XueQiuRealTimeEntity();
        entity.setCode(quote.getCode());
        entity.setTimestampe(quote.getTimestamp());

        XueQiuRealTimeEntity db = xueQiuRealTimeEntityRepository.findById(entity.getCode() + "-" + entity.getTimestampe()).orElse(null);
        if (db == null) {
            entity.setId(entity.getCode() + "-" + entity.getTimestampe());
            entity.setTimes(quote.getTime());
            entity.setName(quote.getName());
            entity.setChg(quote.getChg());
            entity.setCurrent(quote.getCurrent());
            entity.setHigh(quote.getHigh());
            entity.setLow(quote.getLow());
            entity.setPercent(quote.getPercent());
            entity.setHigh52w(quote.getHigh52w());
            entity.setLow52w(quote.getLow52w());
            entity.setOpen(quote.getOpen());
            xueQiuRealTimeEntityRepository.save(entity);
        }
    }

    /**
     * 拉取请求头 cookies
     */
    private void pullHeader() {
        Response response = xueQiuHeaderFeignApi.getHqPage();
        Map<String, Collection<String>> headers = response.headers();
        Collection<String> collection = headers.get("set-cookie");
        if (collection != null && !collection.isEmpty()) {
            List<String> list = new ArrayList<>(collection);
            XueQiuConstant.HEADER_COOKIES = String.join(";", list);
        }
    }


}
