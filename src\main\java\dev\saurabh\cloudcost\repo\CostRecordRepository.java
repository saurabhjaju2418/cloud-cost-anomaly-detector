package dev.saurabh.cloudcost.repo;
import dev.saurabh.cloudcost.domain.CostRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface CostRecordRepository extends JpaRepository<CostRecord,UUID> {
    List<CostRecord> findByProviderAndAccountIdAndServiceAndRegionAndCurrencyAndUsageDateBetweenOrderByUsageDateAsc(String provider,String accountId,String service,String region,String currency,LocalDate from,LocalDate to);
    Optional<CostRecord> findByProviderAndAccountIdAndServiceAndRegionAndUsageDate(String provider,String accountId,String service,String region,LocalDate date);
}

