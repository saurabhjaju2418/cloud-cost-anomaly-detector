package dev.saurabh.cloudcost.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="cost_records", uniqueConstraints=@UniqueConstraint(name="uq_cost_day_dimensions", columnNames={"provider","account_id","service_name","region","usage_date"}))
public class CostRecord {
    @Id public UUID id;
    @Column(nullable=false,length=20) public String provider;
    @Column(name="account_id",nullable=false,length=100) public String accountId;
    @Column(name="service_name",nullable=false,length=100) public String service;
    @Column(nullable=false,length=80) public String region;
    @Column(name="usage_date",nullable=false) public LocalDate usageDate;
    @Column(nullable=false,precision=18,scale=4) public BigDecimal amount;
    @Column(nullable=false,length=3) public String currency;
    @Column(name="source_ref",nullable=false,length=180) public String sourceRef;
    protected CostRecord() {}
    public CostRecord(String provider,String accountId,String service,String region,LocalDate date,BigDecimal amount,String currency,String sourceRef) {
        this.id=UUID.randomUUID(); this.provider=provider; this.accountId=accountId; this.service=service; this.region=region;
        this.usageDate=date; this.amount=amount; this.currency=currency; this.sourceRef=sourceRef;
    }
}

