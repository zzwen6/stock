package top.hting.stock.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 股票数据分析任务
 * 基于天的数据、实时数据分析
 * @author zzwen6
 * @date 2024/6/8 22:03
 */
@Component
public class StockAnalysisJob {

    @Autowired
    StockAnalysisService stockAnalysisService;

    @XxlJob("analyzeStockByDay")
    public ReturnT<String> analyzeStockByDay() {
        stockAnalysisService.analyzeStockByDay();
        return ReturnT.SUCCESS;
    }

    @XxlJob("analyzeStockByRealTime")
    public ReturnT<String> analyzeStockByRealTime() {
        stockAnalysisService.analyzeStockByRealTime();
        return ReturnT.SUCCESS;
    }

}
