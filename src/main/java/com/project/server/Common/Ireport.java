package com.project.server.Common;

import com.project.server.Model.B;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ireport {
    private static final Logger logger = LoggerFactory.getLogger(Ireport.class);

    public static String reportBprintIreportPdf(List<B> listB, String dateStr1, String dateStr2, String number) {
        try {
            String iReportFile = "D:\\project3.0\\server\\ierport\\";
            String pdfPath = "D:\\project3.0\\reportBpdf\\";
            if (!"PathB".equals(number)) {
                logger.info("PdfReport: {}", "PdfReport Start");
                File folder = new File(pdfPath);
                if (!folder.exists() && !folder.isDirectory()) {
                    if (folder.mkdirs()) {
                        logger.info("PdfReport: {}", "目錄創建成功");
                    }
                }
                Date date = new Date();
                String date1date2 = dateStr1.replace("/", "") + dateStr2.replace("/", "");
                String reportBpdf = pdfPath;
                String time = date1date2 + UUID.randomUUID();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String toDay = sdf.format(date);
                Map<String, Object> map = new HashMap<>();
                map.put("title", "Luke Chen's Monthly Report");
                map.put("dateStr1", dateStr1);
                map.put("dateStr2", dateStr2);
                map.put("time", time);
                map.put("toDay", toDay);
                Integer sumCount = listB.size();
                map.put("sumCount", sumCount);

                JRDataSource jrDataSource = new JRBeanCollectionDataSource(listB);
                JasperDesign jasperDesign = JRXmlLoader.load(iReportFile + "reportB.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, jrDataSource);
                JasperExportManager.exportReportToPdfFile(jasperPrint, reportBpdf + time + ".pdf");
                logger.info("PdfReport: {}", "PdfReport Success");
            }
            return pdfPath;
        } catch (Exception e) {
            logger.info("errorMsg: {}", e.getMessage());
            return "";
        }
    }

}
