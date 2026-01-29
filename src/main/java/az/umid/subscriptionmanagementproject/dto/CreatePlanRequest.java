package az.umid.subscriptionmanagementproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePlanRequest {
    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Integer monthlyPrice;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getMonthlyPrice() { return monthlyPrice; }
    public void setMonthlyPrice(Integer monthlyPrice) { this.monthlyPrice = monthlyPrice; }
}
