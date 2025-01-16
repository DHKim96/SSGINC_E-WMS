package com.ssginc.ewms.poi;

import com.ssginc.ewms.income.vo.IncomeFormVO;
import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public interface PoiService {
    ModelAndView makeIncomeFile(IncomeFormVO incomeForm) throws IOException;

    ModelAndView makeOutgoingFile(OutgoingFormVO outgoingForm);
}
