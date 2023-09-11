package top.hting.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.hting.stock.entity.QuoteEntity;

@Repository
public interface QuoteEntityRepository extends JpaRepository<QuoteEntity, String> {

}
