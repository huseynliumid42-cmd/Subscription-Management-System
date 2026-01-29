package az.umid.subscriptionmanagementproject.service;

import az.umid.subscriptionmanagementproject.dto.SubscribeRequest;
import az.umid.subscriptionmanagementproject.entity.Plan;
import az.umid.subscriptionmanagementproject.entity.Subscription;
import az.umid.subscriptionmanagementproject.entity.User;
import az.umid.subscriptionmanagementproject.exception.NotFoundException;
import az.umid.subscriptionmanagementproject.repository.PlanRepository;
import az.umid.subscriptionmanagementproject.repository.SubscriptionRepository;
import az.umid.subscriptionmanagementproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            UserRepository userRepository,
            PlanRepository planRepository
    ) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    public Subscription subscribe(SubscribeRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + req.getUserId()));

        Plan plan = planRepository.findById(req.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan not found: " + req.getPlanId()));

        Subscription sub = new Subscription();
        sub.setUser(user);
        sub.setPlan(plan);

        return subscriptionRepository.save(sub);
    }

    public List<Subscription> getByUser(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public Subscription getById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription not found: " + id));
}
}
