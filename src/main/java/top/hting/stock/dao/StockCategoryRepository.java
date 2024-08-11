package top.hting.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.hting.stock.entity.StockCategory;

/**
 * @author zzwen6
 * @date 2024/8/11 12:02
 */
@Repository
public interface StockCategoryRepository extends JpaRepository<StockCategory,String> {

}
