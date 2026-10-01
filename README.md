<div align="center">

<img src="assets/project-banner.svg" alt="Animated cloud cost anomaly dashboard" width="900" />

# Cloud Cost Anomaly Detector

**Find spend changes early, with a baseline you can explain.**

Java 21 · Spring Boot · PostgreSQL · Flyway · Docker

</div>

An API for daily cloud spend ingestion and explainable anomaly detection. It compares each service/account/region daily amount to the preceding fourteen calendar days in the same currency.

## Implemented

- POST /api/cost-records validates daily spend records and upserts the provider/account/service/region/date key.
- The detector needs at least seven prior daily observations, computes their arithmetic mean, and flags spend only when the increase is both greater than 50% and at least 25 currency units.
- GET /api/anomalies returns the most recently detected anomalies with actual, baseline, absolute delta, and ratio.
- PostgreSQL schema, Flyway migration, health/metrics endpoints, and Docker Compose.

This simple baseline is intentionally visible and deterministic. It is a portfolio MVP, not a replacement for each cloud provider's billing semantics or a seasonality-aware forecasting system.

## Run

Requirements: Docker Compose.

```bash
docker compose up --build
```

Example record:

```bash
curl -X POST http://localhost:8080/api/cost-records \
  -H 'Content-Type: application/json' \
  -d '{"provider":"aws","accountId":"demo-account","service":"compute","region":"eu-west-1","usageDate":"2026-10-01","amount":184.25,"currency":"USD","sourceRef":"demo-export-2026-10-01"}'
```

## Detection flow

```text
daily cost record -> normalized dimensions -> 14-day history -> explainable baseline
                                                          -> threshold decision -> anomaly record
```

## Known boundaries

The current API accepts already-normalized spend; cloud provider credential ingestion, currency conversion, budgets, notifications, multi-tenant authorization, and seasonal models are not implemented. Currency is compared only within the same currency code. A production ingest adapter should verify provider report completeness and support idempotent batch imports.

## License

MIT. See LICENSE.

