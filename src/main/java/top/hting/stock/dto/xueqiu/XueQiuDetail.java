package top.hting.stock.dto.xueqiu;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzwen6
 * @date 2023/8/30 21:54
 */
@Data
public class XueQiuDetail implements Serializable {
    private Object market;

    private Object others;

    private Quote quote;
}
