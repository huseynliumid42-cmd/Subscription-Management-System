package az.umid.subscriptionmanagementproject.Controller;

import az.umid.subscriptionmanagementproject.dto.PayRequest;
import az.umid.subscriptionmanagementproject.dto.PaymentResponse;
import az.umid.subscriptionmanagementproject.entity.Payment;
import az.umid.subscriptionmanagementproject.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse pay(@Valid @RequestBody PayRequest req) {
        return paymentService.pay(req);
    }

    @GetMapping("/user/{userId}") //
    public List<Payment> getUserPayments(@PathVariable Long userId) {
        return paymentService.getByUser(userId);
    }
}
