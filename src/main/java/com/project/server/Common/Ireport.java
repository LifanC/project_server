package com.project.server.Common;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class Ireport {
    private static final Logger logger = LoggerFactory.getLogger(Ireport.class);

    public static void folderMkdirsFunction(String pdf) {
        File folder = new File(pdf);
        if (!folder.exists() && !folder.isDirectory()) {
            if (folder.mkdirs()) {
                logger.info("PdfReport: {},{}", "目錄創建成功", pdf);
            } else {
                logger.info("PdfReport: {},{}", "目錄已存在", pdf);
            }
        }
    }

    public static void exportReportFunctionPDF(
            List<Map<String, Object>> dataList, String iReportFilePath, Map<String, Object> header, String pdfPath
    ) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(iReportFilePath);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataList);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, header, jrDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
        logger.info("exportReportFunction: {}", "PDF輸出成功");
    }

}
