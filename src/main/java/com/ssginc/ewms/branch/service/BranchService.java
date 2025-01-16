package com.ssginc.ewms.branch.service;

import com.ssginc.ewms.branch.vo.BranchVO;

import java.util.List;

public interface BranchService {
    List<BranchVO> findBranchList();
}
