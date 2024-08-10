package top.hting.stock.dto.xueqiu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zzwen6
 * @date 2024/8/10 15:12
 */
@NoArgsConstructor
@Data
public class XueQiuStockList implements Serializable {
    private Integer count;

    private List<XueQiuStock> list;

    @Data
    public static class XueQiuStock implements Serializable {
        @JsonProperty("symbol") // 股票代码
        private String symbol;

        @JsonProperty("net_profit_cagr") // 净利润复合年增长率
        private Double netProfitCagr;

        @JsonProperty("north_net_inflow") // 来自北向资金的净流入
        private Object northNetInflow;

        @JsonProperty("ps") // 市销率
        private Double ps;

        @JsonProperty("type") // 股票类型
        private Integer type;

        @JsonProperty("percent") // 百分比变化
        private Double percent;

        @JsonProperty("has_follow") // 是否已关注该股票
        private Boolean hasFollow;

        @JsonProperty("tick_size") // 最小价格变动单位
        private Double tickSize;

        @JsonProperty("pb_ttm") // 市净率（滚动年）
        private Double pbTtm;

        @JsonProperty("float_shares") // 流通股数
        private Long floatShares;

        @JsonProperty("current") // 当前股价
        private Double current;

        @JsonProperty("amplitude") // 价格振幅
        private Double amplitude;

        @JsonProperty("pcf") // 市现率
        private Double pcf;

        @JsonProperty("current_year_percent") // 本年度百分比变化
        private Double currentYearPercent;

        @JsonProperty("float_market_capital") // 流通市值
        private Double floatMarketCapital;

        @JsonProperty("north_net_inflow_time") // 北向资金净流入时间
        private Object northNetInflowTime;

        @JsonProperty("market_capital") // 总市值
        private Double marketCapital;

        @JsonProperty("dividend_yield") // 股息率
        private Double dividendYield;

        @JsonProperty("lot_size") // 每手股数
        private Integer lotSize;

        @JsonProperty("roe_ttm") // 滚动年净资产收益率
        private Double roeTtm;

        @JsonProperty("total_percent") // 总百分比变化
        private Double totalPercent;

        @JsonProperty("percent5m") // 5分钟百分比变化
        private Integer percent5m;

        @JsonProperty("income_cagr") // 收入复合年增长率
        private Double incomeCagr;

        @JsonProperty("amount") // 成交额
        private Double amount;

        @JsonProperty("chg") // 价格变动
        private Double chg;

        @JsonProperty("issue_date_ts") // 股票发行日期时间戳
        private Long issueDateTs;

        @JsonProperty("eps") // 每股收益
        private Double eps;

        @JsonProperty("main_net_inflows") // 主力资金净流入
        private Double mainNetInflows;

        @JsonProperty("volume") // 成交量
        private Long volume;

        @JsonProperty("volume_ratio") // 成交量与总股数的比率
        private Double volumeRatio;

        @JsonProperty("pb") // 市净率
        private Double pb;

        @JsonProperty("followers") // 股票关注者数量
        private Integer followers;

        @JsonProperty("turnover_rate") // 换手率
        private Double turnoverRate;

        @JsonProperty("mapping_quote_current") // 当前映射报价
        private Object mappingQuoteCurrent;

        @JsonProperty("first_percent") // 初始百分比变化
        private Double firstPercent;

        @JsonProperty("name") // 股票名称
        private String name;

        @JsonProperty("pe_ttm") // 市盈率（滚动年）
        private Double peTtm;

        @JsonProperty("dual_counter_mapping_symbol") // 双重计数映射符号
        private Object dualCounterMappingSymbol;

        @JsonProperty("total_shares") // 总股数
        private Long totalShares;

        @JsonProperty("limitup_days") // 股票涨停天数
        private Integer limitupDays;

    }


}
