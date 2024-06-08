package top.hting.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.hting.stock.entity.XueQiuStockEntity;

/**
 * @author zzwen6
 * @date 2024/6/8 21:15
 */
@Repository
public interface XueQiuStockEntityRepository extends JpaRepository<XueQiuStockEntity, String> {
}
