package dev.saurabh.cloudcost.service;

import dev.saurabh.cloudcost.api.*;
import dev.saurabh.cloudcost.domain.*;
import dev.saurabh.cloudcost.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class CostService {
    private static final int WINDOW_DAYS=14;
    private static final BigDecimal MIN_DELTA=new BigDecimal("25.00");
    private static final BigDecimal MULTIPLIER=new BigDecimal("1.50");
    private final CostRecordRepository costs;
    private final AnomalyRepository anomalies;
    public CostService(CostRecordRepository costs,AnomalyRepository anomalies) { this.costs=costs; this.anomalies=anomalies; }

    @Transactional
    public CostIngestResult ingest(CostIngestRequest req) {
        CostRecord current=costs.findByProviderAndAccountIdAndServiceAndRegionAndUsageDate(req.provider(),req.accountId(),req.service(),req.region(),req.usageDate())
            .map(existing->{ existing.amount=req.amount(); existing.currency=req.currency(); existing.sourceRef=req.sourceRef(); return existing; })
            .orElseGet(()->new CostRecord(req.provider(),req.accountId(),req.service(),req.region(),req.usageDate(),req.amount(),req.currency(),req.sourceRef()));
        current=costs.save(current);
        LocalDate from=req.usageDate().minusDays(WINDOW_DAYS), to=req.usageDate().minusDays(1);
        List<CostRecord> history=costs.findByProviderAndAccountIdAndServiceAndRegionAndCurrencyAndUsageDateBetweenOrderByUsageDateAsc(
            req.provider(),req.accountId(),req.service(),req.region(),req.currency(),from,to);
        boolean detected=false;
        if(history.size()>=7) {
            BigDecimal baseline=history.stream().map(c->c.amount).reduce(BigDecimal.ZERO,BigDecimal::add)
                .divide(BigDecimal.valueOf(history.size()),4,RoundingMode.HALF_UP);
            BigDecimal delta=req.amount().subtract(baseline);
            BigDecimal ratio=baseline.signum()==0 ? (req.amount().signum()>0 ? new BigDecimal("999.9999") : BigDecimal.ZERO)
                : delta.divide(baseline,4,RoundingMode.HALF_UP);
            if(delta.compareTo(MIN_DELTA)>=0 && req.amount().compareTo(baseline.multiply(MULTIPLIER))>0) {
                Anomaly anomaly=anomalies.findByCostRecordId(current.id).orElse(null);
                if(anomaly==null) anomalies.save(new Anomaly(current,baseline,delta,ratio,from));
                else { anomaly.baseline=baseline; anomaly.delta=delta; anomaly.deltaRatio=ratio; anomaly.detectedAt=java.time.Instant.now(); }
                detected=true;
            }
        }
        return new CostIngestResult(current.id,current.usageDate,current.amount,current.currency,detected);
    }
    @Transactional(readOnly=true)
    public List<AnomalyView> listAnomalies() { return anomalies.findTop100ByOrderByDetectedAtDesc().stream().map(AnomalyView::of).toList(); }
}

