package top.hting.stock.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "s_symbol_config")
@DynamicUpdate
public class SymbolConfig extends BaseEntity implements Serializable {

    @Comment("股票代码")
    private String symbol;

    @Comment("股票名称")
    private String name;

    /**
     * 上市状态等
     */
    @Comment("股票状态")
    private String status;

    @Comment("所属市场")
    private String exchange;

}
