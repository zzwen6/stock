package top.hting.stock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 每天结果数汇总
 * @author zzwen6
 * @date 2024/5/17 16:52
 */
@NoArgsConstructor
@Data
@Table(name = "xue_qiu_entity")
@Entity
public class XueQiuEntity implements Serializable {
    @Id
    private String id;
    private String code;
    private Integer dates;
    private Long times;
    private String name;
    private Long timestampe; // 这两个时间还不知什么情况

    private BigDecimal chg; // 涨跌幅
    private BigDecimal current; // 当前价
    private BigDecimal high; // 当日最高
    private BigDecimal low; // 当日最低
    private BigDecimal open; // 开盘
    private BigDecimal percent; // 涨跌百分比
    private BigDecimal high52w; // 52周最高最低价
    private BigDecimal low52w;

    private String symbol;
    private BigDecimal amount;
    private BigDecimal amplitude;
    private String currency;
    private BigDecimal navps;

    private Integer type;
    private Long volume;
    private BigDecimal dividend;
    private BigDecimal eps;
    private String exchange;
    private BigDecimal pb;
    private BigDecimal profit;
    private Integer status;
}
