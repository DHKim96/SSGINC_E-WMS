package com.ssginc.ewms.product.mapper;

import com.ssginc.ewms.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    ProductVO getProductByInventoryId(int inventoryId);
}
