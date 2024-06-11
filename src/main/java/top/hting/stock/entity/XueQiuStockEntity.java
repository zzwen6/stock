package top.hting.stock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 雪球股票实体类，用于需要自动化拉取数据的配置
 * @author zzwen6
 */
@NoArgsConstructor
@Data
@Table(name = "xue_qiu_stock_entity")
@Entity
public class XueQiuStockEntity implements Serializable {

    /**
     * 带有沪/深标记的编码
     */
    @Id
    private String id;

    /**
     * 标准编码
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 一天浮动
     */
    private Double dayPercent;

    /**
     * 三天
     */
    private Double threeDayPercent;

    /**
     * 五天
     */
    private Double fiveDayPercent;

    /**
     * 七天
     */
    private Double sevenDayPercent;

    /**
     * 10天
     */
    private Double tenDayPercent;

}
