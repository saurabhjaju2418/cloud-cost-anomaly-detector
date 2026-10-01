package dev.saurabh.cloudcost.api;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
public record CostIngestResult(UUID recordId,LocalDate usageDate,BigDecimal amount,String currency,boolean anomalyDetected) {}

