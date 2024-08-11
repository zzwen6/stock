package top.hting.stock.schedule;

import com.alibaba.fastjson2.util.DateUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hting.stock.api.xueqiu.XueQiuFeignApi;
import top.hting.stock.api.xueqiu.XueQiuHeaderFeignApi;
import top.hting.stock.config.XueQiuConstant;
import top.hting.stock.dao.StockDayDataRepository;
import top.hting.stock.dao.StockRealTimeDataRepository;
import top.hting.stock.dao.StockConfigRepository;
import top.hting.stock.dto.xueqiu.Quote;
import top.hting.stock.dto.xueqiu.XueQiuDetail;
import top.hting.stock.dto.xueqiu.XueQiuResult;
import top.hting.stock.dto.xueqiu.XueQiuStockList;
import top.hting.stock.entity.StockDayData;
import top.hting.stock.entity.StockRealTimeData;
import top.hting.stock.entity.StockConfig;

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
public class XueQiuStockJob {

    @Autowired
    XueQiuHeaderFeignApi xueQiuHeaderFeignApi;
    @Autowired
    XueQiuFeignApi xueQiuFeignApi;

    @Autowired
    StockDayDataRepository stockDayDataRepository;
    @Autowired
    StockRealTimeDataRepository stockRealTimeDataRepository;
    @Autowired
    StockConfigRepository stockConfigRepository;

    /**
     * 拉取数据-每天一次，3点之后
     */
    @XxlJob("pullDataEveryDay")
    public ReturnT<String> pullData() {
        // 每次拉取请求头，防止失效
        this.pullHeader();

        List<StockConfig> list = stockConfigRepository.findAll();
        for (StockConfig stock : list) {
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
        List<StockConfig> list = stockConfigRepository.findByRealDataSwitch(true);
        for (StockConfig stock : list) {
            String symbol = stock.getId();
            this.getStockRealTimeData(symbol);
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 每天拉取一次股票市场 沪股数据配置 不存在才写入
     */
    @XxlJob("pullStockConfig")
    public ReturnT<String> pullStockConfig() {
        // 每次拉取请求头，防止失效
        this.pullHeader();

        int page = 1;
        Integer size = 90;
        String order ="asc";
        String orderBy = "market_capital"; // 按市值排序
        String market = "CN";
        String type = "sha";

        XueQiuResult<XueQiuStockList> stockList = null;
        do {
            stockList = xueQiuFeignApi.getStockList(page, size, order, orderBy, market, type);
            if (stockList.getData() != null) {
                List<XueQiuStockList.XueQiuStock> list = stockList.getData().getList();
                for (XueQiuStockList.XueQiuStock bean : list) {
                    // 只有数据库不存在的情况下才写入
                    StockConfig stockConfig = stockConfigRepository.findById(bean.getSymbol()).orElse(null);
                    if (stockConfig == null) {
                        stockConfig = new StockConfig(bean.getSymbol(), bean.getName());
                        stockConfigRepository.save(stockConfig);
                    }
                }
            }
            // 下一页
            page ++;
        }while (stockList != null && stockList.getData() != null && stockList.getData().getList().size() > 0);
        return ReturnT.SUCCESS;
    }

    private void getStockDataByDay(String symbol) {
        XueQiuResult<XueQiuDetail> detailResult = xueQiuFeignApi.getDetail(symbol, "detail");
        Quote quote = detailResult.getData().getQuote();

        StockDayData entity = new StockDayData();
        entity.setSymbol(symbol);
        entity.setCode(quote.getCode());
        String date = DateUtils.format(new Date(quote.getTime()), "yyyyMMdd");

        StockDayData db = stockDayDataRepository.findById(entity.getCode() + "-" + date).orElse(null);
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
            stockDayDataRepository.save(entity);
        }
    }

    private void getStockRealTimeData(String symbol) {
        XueQiuResult<XueQiuDetail> detailResult = xueQiuFeignApi.getDetail(symbol, "detail");
        Quote quote = detailResult.getData().getQuote();

        StockRealTimeData entity = new StockRealTimeData();
        entity.setCode(quote.getCode());
        entity.setTimestampe(quote.getTimestamp());

        StockRealTimeData db = stockRealTimeDataRepository.findById(entity.getCode() + "-" + entity.getTimestampe()).orElse(null);
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
            stockRealTimeDataRepository.save(entity);
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
