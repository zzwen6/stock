package top.hting.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.hting.stock.entity.XueQiuEntity;

/**
 * @author zzwen6
 * @date 2024/5/17 17:24
 */
@Repository
public interface XueQiuEntityRepository extends JpaRepository<XueQiuEntity, String> {
}
