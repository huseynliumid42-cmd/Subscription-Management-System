package az.umid.subscriptionmanagementproject.service;

import az.umid.subscriptionmanagementproject.dto.CreatePlanRequest;
import az.umid.subscriptionmanagementproject.entity.Plan;
import az.umid.subscriptionmanagementproject.exception.BusinessException;
import az.umid.subscriptionmanagementproject.exception.NotFoundException;
import az.umid.subscriptionmanagementproject.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    public Plan create(CreatePlanRequest req) {


        if (planRepository.findByName(req.getName()).isPresent()) {
            throw new BusinessException("Plan already exists: " + req.getName());
        }

        Plan plan = new Plan();
        plan.setName(req.getName());
        plan.setMonthlyPrice(req.getMonthlyPrice());

        return planRepository.save(plan);
    }


    public List<Plan> getAll() {
        return planRepository.findAll();
    }



    public Plan getById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Plan not found: " + id)
                );
    }
}