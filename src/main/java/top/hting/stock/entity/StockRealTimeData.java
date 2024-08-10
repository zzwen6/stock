package top.hting.stock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 每天实时结果数据
 * 1分钟更新一次
 * @author zzwen6
 * @date 2024/5/17 16:52
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "s_stock_real_time_data")
public class StockRealTimeData implements Serializable {
    @Id
    private String id;
    private String code;
    private Long timestampe; // 这两个时间还不知什么情况
    private Long times;
    private String name;

    private BigDecimal chg; // 涨跌幅
    private BigDecimal current; // 当前价
    private BigDecimal high; // 当日最高
    private BigDecimal low; // 当日最低
    private BigDecimal open; // 开盘
    private BigDecimal percent; // 涨跌百分比
    private BigDecimal high52w; // 52周最高最低价
    private BigDecimal low52w;

}
