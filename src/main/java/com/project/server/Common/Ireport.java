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

    /**
     * <h3>新增資料夾的方法</h3>
     * @param pdf PDF路徑
     */
    public static void folderMkdirsFunction(String pdf){
        File folder = new File(pdf);
        if (!folder.exists() && !folder.isDirectory()) {
            if (folder.mkdirs()) {
                logger.info("PdfReport: {}", "目錄創建成功");
            }
        }
    }

    /**
     * <h3>輸出報表PDF方法</h3>
     * @param dataList 資料(List)
     * @param iReportFilePath jrxml檔案位置(String)
     * @param paramsMap jrxml固定值(Map)
     * @param pdfPath 輸出PDF檔案位置(String)+名稱(String)
     * @throws JRException Jasper JRException
     */
    public static void exportReportFunction(List<?> dataList, String iReportFilePath, Map<String, Object> paramsMap, String pdfPath) throws JRException {
        logger.info("exportReportFunction: {}", "輸出PDF");
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataList);
        JasperDesign jasperDesign = JRXmlLoader.load(iReportFilePath);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramsMap, jrDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
    }




}
