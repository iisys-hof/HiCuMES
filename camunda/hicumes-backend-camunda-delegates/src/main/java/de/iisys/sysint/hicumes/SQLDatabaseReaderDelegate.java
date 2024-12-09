package de.iisys.sysint.hicumes;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import java.sql.*;
import java.util.*;

public class SQLDatabaseReaderDelegate extends DelegateSuperClass  implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception
    {
        super.init(execution);

        if(!super.isDisabled)
        {
            System.out.println("--------------------------------------------------");
            System.out.println("++++++++++ SQLDatabaseReaderDelegate +++++++++++++");
            System.out.println("--------------------------------------------------");

            ObjectValue typedDatabaseResult =
                    Variables.objectValue(readDatabase(execution)).serializationDataFormat("application/json").create();

            String processVarName = checkForVariable("PROCESS_VAR_NAME", execution);
            if(processVarName != null)
            {
                execution.setVariable(processVarName, typedDatabaseResult);
            }
            else
            {
                execution.setVariable("databaseResult", typedDatabaseResult);
            }
        }
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("++++++++++ SQLDatabaseReaderDelegate +++++++++++++");
            System.out.println("------------ Delegate is disabled!! --------------");
            System.out.println("--------------------------------------------------");
        }
    }

    private List<Map<String, Object>> readDatabase(DelegateExecution execution) {

        String jdbcUrl = null, url = null, port = null, database = null, user = null, password = null, query = null, databaseType = null;

        url = checkForVariable("URL", execution);
        port = checkForVariable("PORT", execution);
        database = checkForVariable("DATABASE", execution);
        user = checkForVariable("USER", execution);
        password = checkForVariable("PASSWORD", execution);
        query = checkForVariable("QUERY", execution);
        databaseType = checkForVariable("DATABASE_TYPE", execution);

        if(databaseType != null && url != null && port != null && database != null)
        {
            switch (databaseType.toLowerCase())
            {
                case "mysql":
                {
                    jdbcUrl = "jdbc:mysql://" + url + ":" + port + "/" + database;
                    break;
                }
                case "postgresql":
                {
                    jdbcUrl = "jdbc:postgresql://" + url + ":" + port + "/" + database;;
                    break;
                }
                case "oracle":
                {
                    jdbcUrl = "jdbc:oracle:thin:@" + url + ":" + port + ":" + database;
                    break;
                }
                case "sqlserver":
                {
                    jdbcUrl = "jdbc:sqlserver://" + url + ":" + port + ";databaseName=" + database;
                    break;
                }
            }
        }

        try {
            // Establish a connection
            Connection connection = null;
            if (jdbcUrl != null) {
                connection = DriverManager.getConnection(jdbcUrl, user, password);
            }
            else
            {
                return null;
            }

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i); // Use getColumnLabel to get the alias
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnLabel, columnValue);
                }
                results.add(row);
            }


            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    String checkForVariable(String variableName, DelegateExecution execution) {
        if(execution.hasVariable(variableName) && execution.getVariable(variableName) != null)
        {
            return (String) execution.getVariable(variableName);
        }
        else return null;
    }
}
