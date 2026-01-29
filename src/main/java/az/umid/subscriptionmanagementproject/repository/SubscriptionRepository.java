package az.umid.subscriptionmanagementproject.repository;


import az.umid.subscriptionmanagementproject.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository <Subscription, Long>{
    List<Subscription> findByUserId(Long userId);


}
