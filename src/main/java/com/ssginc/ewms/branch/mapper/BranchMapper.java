package com.ssginc.ewms.branch.mapper;

import com.ssginc.ewms.branch.vo.BranchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BranchMapper {
    BranchVO getBranchByName(String branchName);

    List<BranchVO> findBranchList();
}
