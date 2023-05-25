import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class ExcelGenerator {

    public static void main(String[] args) throws IOException {
        String templatePath = "/path/to/template.xlsx";
        String outputPath = "/path/to/output.xlsx";

        try (
                InputStream templateStream = new FileInputStream(new File(templatePath));
                OutputStream outputStream = new FileOutputStream(new File(outputPath));
                WorkbookAbstractFactory factory = createWorkbookAbstractFactory()
        ) {
            WorkbookBuilder workbookBuilder = new WorkbookBuilder(factory);
            Workbook workbook = workbookBuilder
                    .setTemplateStream(templateStream)
                    .setOutputPath(outputPath)
                    .addSheet("Sheet 1")
                    .addSheet("Sheet 2")
                    .addSheet("Sheet 3")
                    .build();

            Sheet sheet1 = workbook.getSheet("Sheet 1");
            Sheet sheet2 = workbook.getSheet("Sheet 2");
            Sheet sheet3 = workbook.getSheet("Sheet 3");

            Row row1 = sheet1.getRow(0);
            Row row2 = sheet2.getRow(0);
            Row row3 = sheet3.getRow(0);

            Cell cell1 = row1.getCell(0);
            Cell cell2 = row2.getCell(0);
            Cell cell3 = row3.getCell(0);

            cell1.setCellValue("Hello, world! - Sheet 1");
            cell2.setCellValue("Hello, world! - Sheet 2");
            cell3.setCellValue("Hello, world! - Sheet 3");

            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static WorkbookAbstractFactory createWorkbookAbstractFactory() {
        //specific factory
        return new DefaultWorkbookAbstractFactory();
    }
}

interface WorkbookAbstractFactory {
    Workbook createWorkbook(InputStream templateStream) throws IOException;

    Sheet createSheet(Workbook workbook, String sheetName);
}

class DefaultWorkbookAbstractFactory implements WorkbookAbstractFactory {
    @Override
    public Workbook createWorkbook(InputStream templateStream) throws IOException {
        return WorkbookFactory.create(templateStream);
    }

    @Override
    public Sheet createSheet(Workbook workbook, String sheetName) {
        return workbook.createSheet(sheetName);
    }
}

class WorkbookBuilder {
    private WorkbookAbstractFactory workbookFactory;
    private InputStream templateStream;
    private String outputPath;
    private Workbook workbook;

    public WorkbookBuilder(WorkbookAbstractFactory workbookFactory) {
        this.workbookFactory = workbookFactory;
    }

    public WorkbookBuilder setTemplateStream(InputStream templateStream) {
        this.templateStream = templateStream;
        return this;
    }

    public WorkbookBuilder setOutputPath(String outputPath) {
        this.outputPath = outputPath;
        return this;
    }

    public WorkbookBuilder addSheet(String sheetName) {
        if (workbook == null) {
            workbook = workbookFactory.createWorkbook(templateStream);
        }
        workbookFactory.createSheet(workbook, sheetName);
        return this;
    }

    public Workbook build() {
        return workbook;
    }
}

class SheetDecorator implements Sheet {
    private Sheet decoratedSheet;

    public SheetDecorator(Sheet decoratedSheet) {
        this.decoratedSheet = decoratedSheet;
    }

    // todo Implement the Sheet interface methods by forwarding the calls to the decorated sheet
    @Override
    public void createRow(int rownum) {
        decoratedSheet.createRow(rownum);
    }

    // todo Additional methods to increase the number of rows
    public void createRows(int startRow, int endRow) {
        for (int i = startRow; i <= endRow; i++) {
           
