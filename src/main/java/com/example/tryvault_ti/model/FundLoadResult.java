package com.example.tryvault_ti.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FundLoadResult {
    private Long id;
    private Long customerId;
    private Boolean accepted;

    public FundLoadResult merge(FundLoadResult result) {
        assert this.id.equals(result.getId());
        assert this.customerId.equals(result.getCustomerId());

        this.accepted = this.accepted && result.getAccepted();

        return this;
    }
}
