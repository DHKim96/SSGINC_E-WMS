package com.ssginc.ewms.branch.service;

import com.ssginc.ewms.branch.mapper.BranchMapper;
import com.ssginc.ewms.branch.vo.BranchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchMapper branchMapper;

    public List<BranchVO> findBranchList() {
        return branchMapper.findBranchList();
    }
}
