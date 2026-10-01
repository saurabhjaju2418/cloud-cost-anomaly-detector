package dev.saurabh.cloudcost.repo;
import dev.saurabh.cloudcost.domain.Anomaly;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
public interface AnomalyRepository extends JpaRepository<Anomaly,UUID> {
    List<Anomaly> findTop100ByOrderByDetectedAtDesc();
    java.util.Optional<Anomaly> findByCostRecordId(UUID costRecordId);
}

