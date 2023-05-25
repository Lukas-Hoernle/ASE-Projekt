import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class ExcelGenerator {

    public static void main(String[] args) throws IOException {
        String templatePath = "/path/to/template.xlsx";
        String outputPath = "/path/to/output.xlsx";

        try (
                InputStream templateStream = new FileInputStream(new File(templatePath));
                OutputStream outputStream = new FileOutputStream(new File(outputPath));
                WorkbookFactory factory = createWorkbookFactory()
        ) {
            Workbook workbook = factory.createWorkbook(templateStream);

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            cell.setCellValue("Hello, world!");

            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static WorkbookFactory createWorkbookFactory() {
        //specific factory
        return new DefaultWorkbookFactory();
    }
}

interface WorkbookFactory {
    Workbook createWorkbook(InputStream templateStream) throws IOException;
}

class DefaultWorkbookFactory implements WorkbookFactory {
    @Override
    public Workbook createWorkbook(InputStream templateStream) throws IOException {
        return WorkbookFactory.create(templateStream);
    }
}
