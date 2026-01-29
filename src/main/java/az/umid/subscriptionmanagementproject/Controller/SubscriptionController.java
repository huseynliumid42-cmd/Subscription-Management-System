package az.umid.subscriptionmanagementproject.Controller;

import az.umid.subscriptionmanagementproject.dto.SubscribeRequest;
import az.umid.subscriptionmanagementproject.entity.Subscription;
import az.umid.subscriptionmanagementproject.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subscription subscribe(@Valid @RequestBody SubscribeRequest req) {
        return subscriptionService.subscribe(req);
    }

    @GetMapping("/user/{userId}")
    public List<Subscription> getByUser(@PathVariable Long userId) {
        return subscriptionService.getByUser(userId);
    }
}
