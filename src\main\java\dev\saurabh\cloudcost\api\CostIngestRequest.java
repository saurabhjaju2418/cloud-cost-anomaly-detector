package dev.saurabh.cloudcost.api;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
public record CostIngestRequest(@NotBlank @Size(max=20) String provider,
 @NotBlank @Size(max=100) String accountId,@NotBlank @Size(max=100) String service,
 @NotBlank @Size(max=80) String region,@NotNull @PastOrPresent LocalDate usageDate,
 @NotNull @DecimalMin("0.0000") @Digits(integer=14,fraction=4) BigDecimal amount,
 @NotBlank @Pattern(regexp="[A-Z]{3}") String currency,@NotBlank @Size(max=180) String sourceRef) {}

