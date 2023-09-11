package top.hting.stock.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity implements Serializable {

    private String id;

    @Column(name = "id", length = 40)
    @Id
    @GeneratedValue(generator = "dao-uuid")
    @GenericGenerator(name = "dao-uuid", strategy = "top.hting.stock.config.IdGenerationStrategy")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
