package com.ssginc.ewms.poi;

import com.ssginc.ewms.income.vo.IncomeFormVO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PoiService {
    private final ResourceLoader resourceLoader;

    public ModelAndView makeIncomeFile(IncomeFormVO incomeForm) throws IOException {
        String sample = "src/main/resources/attach/income.docx";
        FileOutputStream fos = null;

        try {
            File file = new File(sample);

            XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
            XWPFTable table = null;

            // -------- 테이블 요소 구하기
            Iterator<IBodyElement> docElementsIterator = doc.getBodyElementsIterator();
            while(docElementsIterator.hasNext()) {
                IBodyElement docElement = docElementsIterator.next();

                if("TABLE".equalsIgnoreCase(docElement.getElementType().name())) {
                    List<XWPFTable> xwpfTableList = docElement.getBody().getTables();

                    table = xwpfTableList.get(0);
                }
            }
            // ----------------------------

            String productName = incomeForm.getProductName();
            String supplierName = incomeForm.getSupplierName();
            String shipperName = "대한통운";
            String incomeType = incomeForm.getIncomeType().equals("normalIncome") ? "일반입고" : "긴급입고";
            String incomeQuantity = String.valueOf(incomeForm.getIncomeQuantity());
            String incomeExpectedDate = incomeForm.getIncomeExpectedDate();

            XWPFTableRow row = null;

            row = table.getRow(0);
            row.getCell(1).setText(productName);

            row = table.getRow(1);
            row.getCell(1).setText(supplierName);

            row = table.getRow(2);
            row.getCell(1).setText(shipperName);

            row = table.getRow(3);
            row.getCell(1).setText(incomeType);

            row = table.getRow(4);
            row.getCell(1).setText(incomeQuantity);

            row = table.getRow(5);
            row.getCell(1).setText(incomeExpectedDate);

            fos = new FileOutputStream(new File(sample));
            doc.write(fos);

            if(fos != null) fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(sample);

        return new ModelAndView("download", "downloadFile", file);
    }
}
