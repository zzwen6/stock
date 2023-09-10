package top.hting.stock.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zzwen6
 * @date 2023/8/31 10:14
 */
@Data
@Entity
@Table(name = "s_user")
public class UserEntity implements Serializable {

    @Id
    @Column(length = 40)
    private String id;

    @Column(length = 40)
    private String name;

    @Column(length = 40)
    private String email;
}
