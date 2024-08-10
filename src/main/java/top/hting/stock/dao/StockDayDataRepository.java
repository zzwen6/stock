package top.hting.stock.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.hting.stock.entity.StockDayData;

import java.util.List;

/**
 * @author zzwen6
 * @date 2024/5/17 17:24
 */
@Repository
public interface StockDayDataRepository extends JpaRepository<StockDayData, String> {

    List<StockDayData> findBySymbolOrderByDatesDesc(String symbol, Pageable pageable);

}
