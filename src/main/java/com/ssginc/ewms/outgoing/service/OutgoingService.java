package com.ssginc.ewms.outgoing.service;

import com.ssginc.ewms.outgoing.mapper.OutgoingMapper;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutgoingService {

    @Autowired
    private final OutgoingMapper outgoingMapper;

    public OutgoingService(OutgoingMapper outgoingMapper) {
        this.outgoingMapper = outgoingMapper;
    }

    public List<OutgoingVO> getOutgoingBySearch(String startDate, String endDate, String productName, String productStatus) {
        return outgoingMapper.getOutgoingList(startDate, endDate, productName, productStatus);
    }

    public void updateOutgoingStatus(int outgoingId, int status) {
        outgoingMapper.updateOutgoingStatus(outgoingId, status);
    }
}
