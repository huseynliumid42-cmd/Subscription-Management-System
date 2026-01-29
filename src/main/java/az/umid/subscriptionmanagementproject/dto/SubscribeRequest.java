package az.umid.subscriptionmanagementproject.dto;

import jakarta.validation.constraints.NotNull;

public class SubscribeRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long planId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
}
