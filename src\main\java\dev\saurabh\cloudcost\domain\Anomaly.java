package dev.saurabh.cloudcost.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="cost_anomalies", uniqueConstraints=@UniqueConstraint(name="uq_anomaly_cost_record",columnNames="cost_record_id"))
public class Anomaly {
    @Id public UUID id;
    @OneToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="cost_record_id",nullable=false) public CostRecord costRecord;
    @Column(name="baseline_amount",nullable=false,precision=18,scale=4) public BigDecimal baseline;
    @Column(name="delta_amount",nullable=false,precision=18,scale=4) public BigDecimal delta;
    @Column(name="delta_ratio",nullable=false,precision=10,scale=4) public BigDecimal deltaRatio;
    @Column(name="window_start",nullable=false) public LocalDate windowStart;
    @Column(name="detected_at",nullable=false) public Instant detectedAt;
    protected Anomaly() {}
    public Anomaly(CostRecord r,BigDecimal baseline,BigDecimal delta,BigDecimal ratio,LocalDate start) {
        this.id=UUID.randomUUID(); this.costRecord=r; this.baseline=baseline; this.delta=delta; this.deltaRatio=ratio;
        this.windowStart=start; this.detectedAt=Instant.now();
    }
}

