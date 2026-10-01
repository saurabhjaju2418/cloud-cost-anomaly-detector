create table cost_records (
 id uuid primary key, provider varchar(20) not null, account_id varchar(100) not null,
 service_name varchar(100) not null, region varchar(80) not null, usage_date date not null,
 amount numeric(18,4) not null check(amount >= 0), currency char(3) not null,
 source_ref varchar(180) not null,
 constraint uq_cost_day_dimensions unique(provider,account_id,service_name,region,usage_date)
);
create index idx_cost_baseline on cost_records(provider,account_id,service_name,region,currency,usage_date);
create table cost_anomalies (
 id uuid primary key, cost_record_id uuid not null unique references cost_records(id) on delete cascade,
 baseline_amount numeric(18,4) not null, delta_amount numeric(18,4) not null,
 delta_ratio numeric(10,4) not null, window_start date not null, detected_at timestamptz not null
);
create index idx_anomaly_recent on cost_anomalies(detected_at desc);

