<div align="center">

<img src="assets/project-banner.svg" alt="Animated Aperture — Cloud Cost Signals banner" width="900" />

# Aperture — Cloud Cost Signals

**Find the spend change. Understand what moved.**

Java · Spring Boot · PostgreSQL · Cloud billing exports

![Project status](https://img.shields.io/badge/status-in%20progress-7a8b71)

</div>

## Product scope

Load billing data, detect changes against seasonal baselines, and show likely cost drivers with supporting records.

## Architecture notes

Replayable ingestion; idempotent daily partitions; robust configurable baselines; explainable anomaly records; least-privilege cloud access.

### Data model sketch

    cost_records(day, provider, account_id, service, region, amount) · baselines(key, window, expected, variance) · alerts(id, key, observed, expected, status)

## Stack

Java · Spring Boot · PostgreSQL · Cloud billing exports

## Build sequence

1. Billing import and normalized schema
2. Baseline and anomaly detector
3. Driver breakdown and alert lifecycle
4. Scheduling, replay, and observability

## Current status

Public repository with an animated README. Product code is being built incrementally, one project at a time. This page records the planned product boundary and engineering milestones.

## License

MIT.
