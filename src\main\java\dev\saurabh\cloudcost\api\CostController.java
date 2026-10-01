package dev.saurabh.cloudcost.api;
import dev.saurabh.cloudcost.service.CostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api")
public class CostController {
 private final CostService service;
 public CostController(CostService service){this.service=service;}
 @PostMapping("/cost-records") @ResponseStatus(HttpStatus.CREATED)
 public CostIngestResult ingest(@Valid @RequestBody CostIngestRequest request){return service.ingest(request);}
 @GetMapping("/anomalies") public List<AnomalyView> anomalies(){return service.listAnomalies();}
}

