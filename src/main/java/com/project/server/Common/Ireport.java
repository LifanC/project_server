package com.project.server.Common;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class Ireport {
    private static final Logger logger = LoggerFactory.getLogger(Ireport.class);

    public static void folderMkdirsFunction(String pdf){
        File folder = new File(pdf);
        if (!folder.exists() && !folder.isDirectory()) {
            if (folder.mkdirs()) {
                logger.info("PdfReport: {}", "目錄創建成功");
            }
        }
    }

    public static void exportReportFunctionPDF(List<?> dataList, String iReportFilePath, Map<String, Object> paramsMap, String pdfPath) throws JRException {
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataList);
        JasperDesign jasperDesign = JRXmlLoader.load(iReportFilePath);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramsMap, jrDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
        logger.info("exportReportFunction: {}", "PDF輸出成功");
    }

}
