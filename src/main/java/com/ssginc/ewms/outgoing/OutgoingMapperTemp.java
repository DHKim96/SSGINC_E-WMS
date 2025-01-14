package com.ssginc.ewms.outgoing;

import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import com.ssginc.ewms.outgoing.vo.OutgoingRequestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutgoingMapperTemp {
    OutgoingFormVO getOutgoingFormByProductId(int inventoryId);

    int insertOutgoingRequest(OutgoingRequestVO outgoingRequestVO);
}
