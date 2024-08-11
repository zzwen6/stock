package top.hting.stock.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 股票分类：
 * 行业、概念、地域等
 * @author zzwen6
 * @date 2024/8/11 11:58
 */
@Data
@Table(name = "s_stock_category")
@Entity
public class StockCategory {

    /**
     *  分类的代码
     */
    @Id
    private String id;

    /**
     * 分类的名称
     */
    private String name;

    /**
     * 分类：1行业，2概念，3地域
     */
    private int type;

    /**
     * 路径
     */
    private String url;

    public StockCategory() {
    }

    public StockCategory(String id, String name, int type, String url) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
    }
}
