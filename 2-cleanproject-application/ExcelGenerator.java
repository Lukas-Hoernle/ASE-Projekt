import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class ExcelGenerator {

    public static void main(String[] args) throws IOException {
        String templatePath = "/path/to/template.xlsx";
        String outputPath = "/path/to/output.xlsx";

        try (
                InputStream templateStream = new FileInputStream(new File(templatePath));
                OutputStream outputStream = new FileOutputStream(new File(outputPath));
                Workbook workbook = WorkbookFactory.create(templateStream)
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            cell.setCellValue("Hello, world!");

            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
