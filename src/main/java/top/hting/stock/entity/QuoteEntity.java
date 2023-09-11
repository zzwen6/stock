package top.hting.stock.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zzwen6
 * @date 2023/8/1 23:45
 */
@Data
@Entity
@Table(name = "s_quote")
public class QuoteEntity extends BaseEntity implements Serializable {

    /**
     * 主键：股票代码+yyyyMMdd
     */

    /**
     * 股票日期
     */
    private Integer stockDate;

    private BigDecimal currentExt; // 扩展的当前值（如果有）
    private String symbol; // 股票代码
    private BigDecimal volumeExt; // 扩展的成交量（如果有）
    private BigDecimal high52w; // 52周最高价
    private Integer delayed; // 行情数据延迟标记
    private Integer type; // 股票类型
    private BigDecimal tickSize; // 最小价格变动
    private BigDecimal floatShares; // 流通股本
    private BigDecimal limitDown; // 跌停价
    private String noProfit; // 无盈利指标（如果有）
    private BigDecimal high; // 当天最高价
    private BigDecimal floatMarketCapital; // 流通市值
    private Long timestampExt; // 扩展的时间戳（如果有）
    private Integer lotSize; // 每手股数
    private String lockSet; // 锁定集合（如果有）
    private String weightedVotingRights; // 加权投票权益（如果有）
    private BigDecimal chg; // 价格变动
    private BigDecimal eps; // 每股收益
    private BigDecimal lastClose; // 上一个收盘价
    private BigDecimal profitFour; // 四年利润
    private Integer volume; // 当前成交量
    private BigDecimal volumeRatio; // 成交量比率
    private BigDecimal profitForecast; // 预测利润
    private BigDecimal turnoverRate; // 换手率
    private BigDecimal low52w; // 52周最低价
    private String name; // 股票名称
    private String exchange; // 交易所
    private BigDecimal peForecast; // 预测市盈率
    private Long totalShares; // 总股本
    private Integer status; // 股票状态
    private String isVieDesc; // 是否为VIE的描述（如果有）
    private String securityStatus; // 证券状态（如果有）
    private String code; // 股票代码
    private BigDecimal goodwillInNetAssets; // 净资产中的商誉
    private BigDecimal avgPrice; // 平均价格
    private BigDecimal percent; // 百分比变动
    private String weightedVotingRightsDesc; // 加权投票权益的描述（如果有）
    private BigDecimal amplitude; // 价格振幅
    private BigDecimal current; // 当前价格
    private String isVie; // 是否为VIE（如果有）
    private BigDecimal currentYearPercent; // 当年百分比变动
    private Long issueDate; // 股票发行日期
    private String subType; // 股票子类型
    private BigDecimal low; // 当天最低价
    private String isRegistrationDesc; // 是否注册的描述
    private String noProfitDesc; // 无盈利指标的描述（如果有）
    private BigDecimal marketCapital; // 市值
    private BigDecimal dividend; // 分红
    private BigDecimal dividendYield; // 股息率
    private String currency; // 货币
    private BigDecimal navps; // 每股净资产
    private BigDecimal profit; // 利润
    private Long timestamp; // 行情时间戳
    private BigDecimal peLyr; // 市盈率（上一年度）
    private BigDecimal amount; // 成交金额
    private BigDecimal pledgeRatio; // 质押比例（如果有）
    private BigDecimal tradedAmountExt; // 扩展的成交金额（如果有）
    private String isRegistration; // 是否注册
    private BigDecimal pb; // 市净率
    private BigDecimal limitUp; // 涨停价
    private BigDecimal peTtm; // 市盈率（滚动12个月）
    private Long time; // 行情时间
    private BigDecimal open; // 当天开盘价

    @Column(name = "delayed_flag")
    public Integer getDelayed() {
        return delayed;
    }

    public void setDelayed(Integer delayed) {
        this.delayed = delayed;
    }
}
