package top.hting.stock.dto.xueqiu;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzwen6
 * @date 2023/8/30 21:52
 */
@Data
public class XueQiuResult<T> implements Serializable {

    private String error_code;

    private String error_description;

    private T data;

}
