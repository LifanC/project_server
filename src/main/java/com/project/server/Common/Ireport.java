package com.project.server.Common;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Ireport {
    private static final Logger logger = LoggerFactory.getLogger(Ireport.class);

    public static void exportReportFunctionPDF(
            List<Map<String, Object>> dataList, String iReportFilePath, Map<String, Object> header, String pdfPath
    ) throws JRException {
        JasperPrint jasperPrint = generateJasperPrint(dataList, iReportFilePath, header);
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
        logger.info("exportReportFunction: {}", "PDF輸出成功");
    }

    private static JasperPrint generateJasperPrint(List<Map<String, Object>> dataList,
                                                   String iReportFilePath,
                                                   Map<String, Object> header) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(iReportFilePath);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataList);
        return JasperFillManager.fillReport(jasperReport, header, jrDataSource);
    }

}
