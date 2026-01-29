package az.umid.subscriptionmanagementproject.dto;

import jakarta.validation.constraints.NotNull;

public class PayRequest {
    @NotNull

    private Long subscriptionId;

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
