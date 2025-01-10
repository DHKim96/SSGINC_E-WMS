package com.ssginc.ewms.outgoing.service;

import com.ssginc.ewms.outgoing.mapper.OutgoingMapper;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OutgoingService {

    @Autowired
    private final OutgoingMapper outgoingMapper;

    public OutgoingService(OutgoingMapper outgoingMapper) {
        this.outgoingMapper = outgoingMapper;
    }

    public List<OutgoingVO> getOutgoingList() {
        return outgoingMapper.getOutgoingList();
    }

    public List<OutgoingVO> getOutgoingByDateRange(String startDate, String endDate) {
        return outgoingMapper.selectByDateRange(startDate, endDate);
    }
}
