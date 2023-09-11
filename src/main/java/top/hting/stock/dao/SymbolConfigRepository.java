package top.hting.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.hting.stock.entity.SymbolConfig;

@Repository
public interface SymbolConfigRepository extends JpaRepository<SymbolConfig, String> {
}
