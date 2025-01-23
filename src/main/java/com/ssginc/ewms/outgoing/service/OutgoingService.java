package com.ssginc.ewms.outgoing.service;

import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OutgoingService {
    List<OutgoingVO> getOutgoingBySearch(String startDate, String endDate, String productName, String productStatus);

    void updateOutgoingStatus(int outgoingId, int status);

    List<OutgoingVO> getOutgoingWithInventory(String startDate, String endDate, String productName, String productStatus);

    @Transactional
    void updateOutgoingStatusAndQuantity(int outgoingId, int status);

    OutgoingFormVO getOutgoingFormByInventoryId(int inventoryId);

    @Transactional(rollbackFor = Exception.class)
    int insertOutgoingRequest(OutgoingFormVO outgoingForm);

    OutgoingFormVO getOutgoingFormByProductId(int productId);
}
