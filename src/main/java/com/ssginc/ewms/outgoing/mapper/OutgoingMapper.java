package com.ssginc.ewms.outgoing.mapper;

import com.ssginc.ewms.outgoing.dto.DestinationResponseDto;
import com.ssginc.ewms.outgoing.dto.TransportationRequestDto;
import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import com.ssginc.ewms.outgoing.vo.OutgoingRequestVO;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OutgoingMapper {

    List<OutgoingVO> getOutgoingList(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("productName") String productName,
            @Param("productStatus") String productStatus
    );

    void updateOutgoingStatus(@Param("outgoingId") int outgoingId, @Param("status") int status);

    List<OutgoingVO> getOutgoingWithInventory(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("productName") String productName,
            @Param("productStatus") String productStatus
    );

    void updateQuantity(@Param("outgoingId") int outgoingId);

    Integer getInventoryQuantity(@Param("outgoingId") int outgoingId);
    Integer getOutgoingQuantity(@Param("outgoingId") int outgoingId);
    void updateOutgoingDate(@Param("outgoingId") int outgoingId, @Param("outgoingDate") String outgoingDate);

    OutgoingFormVO getOutgoingFormByProductId(int inventoryId);

    int insertOutgoingRequest(OutgoingRequestVO outgoingRequestVO);

    DestinationResponseDto selectLocationInfo(int outgoingId);

    int insertTransportation(TransportationRequestDto transportationRequestDto);
}