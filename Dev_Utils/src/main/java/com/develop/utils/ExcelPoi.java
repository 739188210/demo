//package com.develop.utils;
//
//import com.alibaba.excel.EasyExcel;
//import com.neusoft.jereh.aftersales.assistant.entity.checklocation.CheckLocation;
//import com.neusoft.jereh.aftersales.assistant.entity.checklocation.CheckLocationItem;
//import com.neusoft.jereh.aftersales.assistant.entity.inspectrecord.DeviceInspectDetail;
//import com.neusoft.jereh.aftersales.assistant.entity.inspectrecord.DeviceInspectRecord;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.streaming.SXSSFRow;
//import org.apache.poi.xssf.streaming.SXSSFSheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import javax.swing.plaf.synth.Region;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
///**
// * @author: miao
// * @date 2022/3/15
// */
//
//public class ExcelPoi {
//    private  static  String path ="F:\\DevTools\\JetBrains\\WorkSpace\\after_sale_server\\after-sales-assistant";
//    public static void main(String[] args) throws Exception {
//     //   dynamicHeadWrite();
//        skod();
//    }
//    public  static  void dynamicHeadWrite() {
//        String fileName =  path + "\\dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";
//        EasyExcel.write(fileName)
//                // 这里放入动态头
//                .head(head()).sheet("模板")
//                // 当然这里数据也可以用 List<List<String>> 去传入
//                .doWrite(Collections.EMPTY_LIST);
//    }
//
//    private static  List<List<String>> head() {
//        List<List<String>> list = new ArrayList<List<String>>();
//        List<String> head0 = new ArrayList<String>();
//        head0.add("字符串" + System.currentTimeMillis());
//        head0.add("字符串2" + System.currentTimeMillis());
//        List<String> head1 = new ArrayList<String>();
//        head1.add("数字" + System.currentTimeMillis());
//        head1.add("数字2" + System.currentTimeMillis());
//        head1.add("数字3" + System.currentTimeMillis());
//        List<String> head2 = new ArrayList<String>();
//        head2.add("日期" + System.currentTimeMillis());
//        list.add(head0);
//        list.add(head1);
//        list.add(head2);
//        return list;
//    }
//
///*    private List<DemoData> data() {
//        List<DemoData> list = new ArrayList<DemoData>();
//        for (int i = 0; i < 10; i++) {
//            DemoData data = new DemoData();
//            data.setString("字符串" + i);
//            data.setDate(new Date());
//            data.setDoubleData(0.56);
//            list.add(data);
//        }
//        return list;
//    }*/
//
//
//    public static void skod() throws Exception {
//        // 声明一个工作薄
//        Workbook workbook = new SXSSFWorkbook();
//        Sheet sheet = workbook.createSheet();
//        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 14));
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 16));
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 15, 16));
//        Row row0 = sheet.createRow(0);
//        Row row1 = sheet.createRow(1);
//        Cell cell0 = row0.createCell(0);
//        cell0.setCellValue("柱塞泵设备基础信息");
//
//     //   Row row1 = sheet.createRow(0);
//        Cell cell1 = row0.createCell(15);
//
//        cell1.setCellValue("柱塞泵设备检查位置信息");
//
//        Cell cell2 = row1.createCell(15);
//        cell2.setCellValue("1.外观检查");
//
//        FileOutputStream fileOutputStream = new FileOutputStream(path + "dshj.xlsx");
//        workbook.write(fileOutputStream);
//        workbook.close();
//
//    }
//
//
////    private void skod(DeviceInspectRecord deviceInspectRecord) {
////        // 声明一个工作薄
////        Workbook workbook = new SXSSFWorkbook();
////        try {
////            AtomicInteger currColNum = new AtomicInteger(15);
////            List<String> columnList = new ArrayList<>();
////            Sheet sheet = workbook.createSheet();
////            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 14));
////            Row row0 = sheet.createRow(0);
////            Cell cell0 = row0.createCell(0);
////            cell0.setCellValue("柱塞泵设备基础信息");
////
////            sheet.addMergedRegion(new CellRangeAddress(0, 0, currColNum.get(), deviceInspectRecord.getInspectDetailNum()));
////            Cell cell1 = row0.createCell(currColNum.get());
////            cell1.setCellValue("柱塞泵设备检查位置信息");
////
////            List<Cell> list = new ArrayList<>();
////            Map<String, LinkedHashMap<String, List<DeviceInspectDetail>>> checkLocMap = deviceInspectRecord.getCheckLocMap();
////            Long specId = deviceInspectRecord.getDeviceBasic().getSpecId();
////
////            List<CheckLocation> checkLocations = checkLocationService.selectList(new CheckLocation().setSpecId(deviceInspectRecord.getSpecId()));
////            List<Long> checkLocIds = checkLocations.stream().mapToLong(CheckLocation::getId).boxed().collect(Collectors.toList());
////            Map<String, LinkedHashMap<String, ArrayList<CheckLocationItem>>> map = new LinkedHashMap<>();
////            LinkedHashMap<String, List<CheckLocationItem>> itemTypeMap = checkLocationItemService.selectWithCheckLocIds(checkLocIds);
//////            itemTypeMap
//////
//////            checkLocMap.entrySet().forEach(entry -> {
//////                if (entry.getValue().size()<2) return ;
//////                sheet.addMergedRegion(new CellRangeAddress(1, 1, currColNum.get(), currColNum.get() + (entry.getValue().size() - 1)));
//////                Cell tempCell = sheet.createRow(1).createCell(currColNum.get());
//////                tempCell.setCellValue(entry.getKey());
//////                currColNum.set(entry.getValue().size() - 1);
//////                list.add(tempCell);
//////            });
////
////            FileOutputStream fileOutputStream = new FileOutputStream(path + "dshj.xlsx");
////            workbook.write(fileOutputStream);
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            try {
////                workbook.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
////
////    }
//}
