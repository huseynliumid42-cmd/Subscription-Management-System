package az.umid.subscriptionmanagementproject.Controller;

import az.umid.subscriptionmanagementproject.dto.CreatePlanRequest;
import az.umid.subscriptionmanagementproject.entity.Plan;
import az.umid.subscriptionmanagementproject.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Plan create(@Valid @RequestBody CreatePlanRequest req) {
        return planService.create(req);
    }

    @GetMapping
    public List<Plan> getAll() {
        return planService.getAll();

    }
}
