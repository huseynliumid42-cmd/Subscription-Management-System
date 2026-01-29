package az.umid.subscriptionmanagementproject.service;

import az.umid.subscriptionmanagementproject.dto.PayRequest;
import az.umid.subscriptionmanagementproject.dto.PaymentResponse;
import az.umid.subscriptionmanagementproject.entity.Payment;
import az.umid.subscriptionmanagementproject.entity.Subscription;
import az.umid.subscriptionmanagementproject.enums.PaymentStatus;
import az.umid.subscriptionmanagementproject.exception.NotFoundException;
import az.umid.subscriptionmanagementproject.repository.PaymentRepository;
import az.umid.subscriptionmanagementproject.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

        private final PaymentRepository paymentRepository;
        private final SubscriptionRepository subscriptionRepository;

        public PaymentService(
                PaymentRepository paymentRepository,
                SubscriptionRepository subscriptionRepository
        ) {
                this.paymentRepository = paymentRepository;
                this.subscriptionRepository = subscriptionRepository;
        }

        public PaymentResponse pay(PayRequest req) {


                Subscription sub = subscriptionRepository.findById(req.getSubscriptionId())
                        .orElseThrow(() ->
                                new NotFoundException("Subscription not found: " + req.getSubscriptionId())
                        );


                Payment payment = new Payment();
                payment.setUser(sub.getUser());
                payment.setSubscription(sub);
                payment.setAmount(sub.getPlan().getMonthlyPrice());
                payment.setStatus(PaymentStatus.SUCCESS);
                payment.setPaidAt(LocalDateTime.now());

                Payment saved = paymentRepository.save(payment);


                PaymentResponse res = new PaymentResponse();
                res.setPaymentId(saved.getId());
                res.setUserId(saved.getUser().getId());
                res.setSubscriptionId(saved.getSubscription().getId());
                res.setAmount(saved.getAmount());
                res.setStatus(saved.getStatus());
                res.setPaidAt(saved.getPaidAt());

                return res;
        }
        public List<Payment> getByUser(Long userId) {
                return paymentRepository.findByUserIdOrderByPaidAtDesc(userId);
        }
}
