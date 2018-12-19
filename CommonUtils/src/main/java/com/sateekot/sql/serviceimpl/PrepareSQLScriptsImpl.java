package com.sateekot.sql.serviceimpl;

import com.sateekot.sql.services.PrepareSQLScripts;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author sateekot
 * date 13-12-2018
 */
public class PrepareSQLScriptsImpl implements PrepareSQLScripts {

    private static final String UPDATE = "UPDATE";
    private static final List<String> columnsWithColon = Arrays.asList("string","date","datetime","timestamp");

    public String prepareInsertScripts(String fileName) {
        return null;
    }

    public String prepareUpdateScripts(String fileName, String tableName, int updateFieldCount, int conditionFieldCount) {
        //int totalFieldCount = updateFieldCount+conditionFieldCount;
        StringBuilder finalUpdateStmts = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));

            Supplier<Stream<String>> streamSupplier = lines::stream;

            List<String> columns = streamSupplier.get().findFirst().map((line) -> Arrays.asList(line.split(","))).get();
            System.out.println(streamSupplier.get().findFirst());
            List<String> dataTypes = streamSupplier.get().skip(1).findFirst().map((line) -> Arrays.asList(line.split(","))).get();
            StringBuilder updateStatement = new StringBuilder(String.format("UPDATE %s SET ",tableName));
            String setStatement = "";
            for(int i = 0; i < updateFieldCount; i++) {
                if(columnsWithColon.contains(dataTypes.get(i).toLowerCase())) {
                    setStatement = setStatement.isEmpty() ?  columns.get(i) +" = '%s'" : setStatement + ", "+columns.get(i) +" = '%s'";
                } else {
                    setStatement = setStatement.isEmpty() ?  columns.get(i) +" = %s" : setStatement + ", "+columns.get(i) +" = %s";
                }
            }
            updateStatement.append(setStatement);

            String conditionStatement = "";
            for(int i = 0; i < conditionFieldCount; i++) {
                if(columnsWithColon.contains(dataTypes.get(updateFieldCount + i))) {
                    conditionStatement = conditionStatement.isEmpty() ?  columns.get(updateFieldCount + i) +" = '%s'" : conditionStatement + ", "+columns.get(updateFieldCount + i) +" = '%s'";
                } else {
                    conditionStatement = conditionStatement.isEmpty() ?  columns.get(updateFieldCount + i) +" = %s" : conditionStatement + ", "+columns.get(updateFieldCount + i) +" = %s";
                }
            }
            updateStatement.append(" WHERE ").append(conditionStatement);

            System.out.println(updateStatement.toString());
            streamSupplier.get().skip(2).forEach( row -> {
                String[] rowValues = row.split(",");
                String updateStmt = String.format(updateStatement.toString(),rowValues[0],rowValues[1]
                        ,rowValues[2],rowValues[3],rowValues[4],rowValues[5]);
                if(finalUpdateStmts.length() == 0) {
                    finalUpdateStmts.append(updateStmt);
                } else {
                    finalUpdateStmts.append("\n");
                    finalUpdateStmts.append(updateStmt);
                }
            });
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return finalUpdateStmts.toString();
    }
}
