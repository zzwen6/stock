package top.hting.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.hting.stock.entity.XueQiuRealTimeEntity;

/**
 * @author zzwen6
 * @date 2024/5/17 17:25
 */
@Repository
public interface XueQiuRealTimeEntityRepository extends JpaRepository<XueQiuRealTimeEntity,String> {
    XueQiuRealTimeEntity findFirstByCodeOrderByTimestampeDesc(String code);
}
