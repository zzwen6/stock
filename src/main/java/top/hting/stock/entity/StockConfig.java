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
@Data
@Table(name = "s_stock_config")
@Entity
public class StockConfig implements Serializable {

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
     * 获取实时数据
     */
    private Boolean realDataSwitch;

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

    public StockConfig(String id, String name) {
        this.id = id;
        this.name = name;
        this.code = id.substring(2);
        this.dayPercent = 3.0;
        this.threeDayPercent = 7.0;
        this.fiveDayPercent = 10.0;
        this.sevenDayPercent = 12.0;
        this.tenDayPercent = 20.0;
    }

    public StockConfig() {
    }
}
