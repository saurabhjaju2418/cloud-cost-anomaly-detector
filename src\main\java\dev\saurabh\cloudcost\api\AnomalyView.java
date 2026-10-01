package dev.saurabh.cloudcost.api;
import dev.saurabh.cloudcost.domain.Anomaly;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
public record AnomalyView(UUID id,String provider,String accountId,String service,String region,LocalDate usageDate,
 BigDecimal actual,BigDecimal baseline,BigDecimal delta,BigDecimal deltaRatio,String currency,Instant detectedAt) {
 public static AnomalyView of(Anomaly a) { var r=a.costRecord; return new AnomalyView(a.id,r.provider,r.accountId,r.service,r.region,r.usageDate,r.amount,a.baseline,a.delta,a.deltaRatio,r.currency,a.detectedAt); }
}

