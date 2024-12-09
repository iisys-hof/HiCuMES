package de.iisys.sysint.hicumes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DBtoExcelFileDelegate extends DelegateSuperClass  implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
        super.init(execution);

        if (!super.isDisabled) {
            System.out.println("--------------------------------------------------");
            System.out.println("++++++++++++ DBtoExcelFileDelegate +++++++++++++++");
            System.out.println("--------------------------------------------------");
            String processVarName = (String) checkForVariable("PROCESS_VAR_NAME", execution);
            List<Map<String, Object>> dbResult = (List<Map<String, Object>>) checkForVariable(processVarName, execution);

            try {
                String fileName = (String) checkForVariable("FILE_NAME", execution);
                if(fileName == null)
                {
                    fileName = "DBResult";
                }
                String path = createExcelFile(dbResult, fileName);
                System.out.println("Excel file created successfully! Location: " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("++++++++++++ DBtoExcelFileDelegate +++++++++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }

    Object checkForVariable(String variableName, DelegateExecution execution) {
        if (execution.hasVariable(variableName) && execution.getVariable(variableName) != null) {
            return execution.getVariable(variableName);
        } else {
            return null;
        }
    }

    public String createExcelFile(List<Map<String, Object>> data, String fileName) throws IOException {
        if (data == null || data.isEmpty()) {
            return null;
        }

        //File currDir = new File(".");
        //String path = currDir.getAbsolutePath();
        String fileLocation = fileName + ".xlsx";

        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation));

             Workbook wb = new Workbook(os, "MyApplication", "1.0")) {
            Worksheet sheet = wb.newWorksheet("Sheet 1");

            // Create header row
            Map<String, Object> headerData = data.get(0);
            int headerCellIdx = 0;
            for (String key : headerData.keySet()) {
                sheet.value(0, headerCellIdx, key);
                headerCellIdx++;
            }

            // Fill data rows
            int rowIdx = 1;
            for (Map<String, Object> rowData : data) {
                int cellIdx = 0;
                for (Object value : rowData.values()) {
                    if (value instanceof String) {
                        sheet.value(rowIdx, cellIdx, (String) value);
                    } else if (value instanceof Integer) {
                        sheet.value(rowIdx, cellIdx, (Integer) value);
                    } else if (value instanceof Double) {
                        sheet.value(rowIdx, cellIdx, (Double) value);
                    }
                    else if (value instanceof Timestamp) {
                        sheet.value(rowIdx, cellIdx, ((Timestamp) value).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")));
                    }
                    else {
                        sheet.value(rowIdx, cellIdx, value.toString());
                    }
                    // Add more types as needed
                    cellIdx++;
                }
                rowIdx++;
            }
            return Paths.get(fileLocation).toString();
        }
    }
}