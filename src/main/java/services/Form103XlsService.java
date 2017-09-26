package services;

import entities.Form103XlsCellBodyDescription;
import entities.Form103XlsSheet;
import model.Form103Model;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Form103XlsService {

    public static void main(String[] args) {
        Form103Model form103Model = new Form103Model();
        generateForm103XlsFile(form103Model.getSheet());
    }

    private static void generateForm103XlsFile(Form103XlsSheet form103XlsSheet){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Form103");

        Row rowDirection = sheet.createRow(0);
        rowDirection.createCell(0).setCellValue("Направление");
        rowDirection.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getDirection());

        Row rowTypeRegisterMail = sheet.createRow(1);
        rowTypeRegisterMail.createCell(0).setCellValue("Вид РПО");
        rowTypeRegisterMail.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getTypeRegisteredMail());

        Row rowCategoryRegisterMail = sheet.createRow(2);
        rowCategoryRegisterMail.createCell(0).setCellValue("Категория РПО");
        rowCategoryRegisterMail.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getCategoryRegisteredMail());

        Row rowSender = sheet.createRow(3);
        rowSender.createCell(0).setCellValue("Отправитель");
        rowSender.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getSender());

        rowSender.createCell(2).setCellValue("Сотовый номер №1 отпр");
        rowSender.createCell(3).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getPhoneNumberFirstSender());

        Row rowAppointmentsRegion= sheet.createRow(4);
        rowAppointmentsRegion.createCell(0).setCellValue("Регион назначения");
        rowAppointmentsRegion.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getAppointmentsRegion());

        rowAppointmentsRegion.createCell(2).setCellValue("Сотовый номер №2 отпр.");
        rowAppointmentsRegion.createCell(3).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getPhoneNumberSecondSender());

        Row rowIndexOPSPlace = sheet.createRow(5);
        rowIndexOPSPlace.createCell(0).setCellValue("Индекс места ОПС приема");
        rowIndexOPSPlace.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getIndexOPSPlace());

        rowIndexOPSPlace.createCell(2).setCellValue("e-mail отпр.");
        rowIndexOPSPlace.createCell(3).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getSenderEmail());

        Row rowAllRegisteredMail = sheet.createRow(6);
        rowAllRegisteredMail.createCell(0).setCellValue("Всего РПО");
        rowAllRegisteredMail.createCell(1).setCellValue(form103XlsSheet.getForm103XlsCellHeaderDescription().getAllRegisteredMail());

        Row rowBodyTableDescription= sheet.createRow(7);
        rowBodyTableDescription.createCell(0).setCellValue("№ п.п.");
        rowBodyTableDescription.createCell(1).setCellValue("Адресат");
        rowBodyTableDescription.createCell(2).setCellValue("Индекс ОПС места назн.");
        rowBodyTableDescription.createCell(3).setCellValue("Адрес места назначения");
        rowBodyTableDescription.createCell(4).setCellValue("ШПИ");
        rowBodyTableDescription.createCell(5).setCellValue("Вес (кг.)");
        rowBodyTableDescription.createCell(6).setCellValue("Сумма объявленной ценности");
        rowBodyTableDescription.createCell(7).setCellValue("Сумма нал. платежа");
        rowBodyTableDescription.createCell(8).setCellValue("Особые отметки");
        rowBodyTableDescription.createCell(9).setCellValue("Сотовый номер №1");
        rowBodyTableDescription.createCell(10).setCellValue("Сотовый номер №2");
        rowBodyTableDescription.createCell(11).setCellValue("E-mail");

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        rowBodyTableDescription.setRowStyle(style);

        for (int i = 0; i < 12; i++) {
            rowBodyTableDescription.getCell(i).setCellStyle(style);
        }
        for (int i = 0; i < 12; i++) {
            sheet.autoSizeColumn(i);
        }

        int r = 8;
        for (Form103XlsCellBodyDescription f : form103XlsSheet.getForm103XlsCellBodyDescription()) {

            Row row = sheet.createRow(r);

            row.createCell(0).setCellValue(f.getRecipientId());
            row.createCell(1).setCellValue(f.getAddressee());
            row.createCell(2).setCellValue(f.getIndexOPSPlace());
            row.createCell(3).setCellValue(f.getDestinationAddress());
            row.createCell(4).setCellValue(f.getBarcode());
            row.createCell(5).setCellValue(f.getWeight());
            row.createCell(6).setCellValue(f.getAmountDeclaredValue());
            row.createCell(7).setCellValue(f.getAmountTaxPayment());
            row.createCell(8).setCellValue(f.getSpecialNotes());
            row.createCell(9).setCellValue(f.getPhoneNumberFirst());
            row.createCell(10).setCellValue(f.getPhoneNumberSecond());
            row.createCell(11).setCellValue(f.getEmail());
            r++;
        }


        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("form103.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
            out.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
