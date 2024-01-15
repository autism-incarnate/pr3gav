package ru.pr3.framework.util;

import org.testng.Assert;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableComparator {

    //Method for comparing two tables represented as an array of array of strings. It's ugly, I know.
    //boolean expectSizeMatch - flag indicating whether we expect tables to have different amount of rows or not
    public static boolean compareTables(List<List<String>> t1, List<List<String>> t2, boolean expectSizeMatch) {
        if(t1 == null || t2 == null) {
            Assert.assertNull(t1, "First table is empty or null! Nothing to compare.");
            Assert.assertNull(t2, "Second table is empty or null! Nothing to compare.");
            return false;
        }

        int t1Size = t1.size();
        int t2Size = t2.size();

        if(t1Size <= 0 || t2Size <= 0) {
            Assert.assertEquals(t1Size, 0, "First table is empty! Nothing to compare.");
            Assert.assertEquals(t2Size, 0, "Second table is empty! Nothing to compare.");
            return false;
        }

        int cSize = t1Size;

        //Switch in case we want to compare only old values, in case they were modified too
        if(t1Size != t2Size && !expectSizeMatch)
            cSize = t1Size < t2Size ? t1Size : t2Size;
        else if(t1Size != t2Size && expectSizeMatch)
            return false;

        for(int i = 0; i < cSize; i++) {
            List<String> r1 = t1.get(i);
            List<String> r2 = t2.get(i);
            int r1Size = r1.size();
            int r2Size = r2.size();

            Assert.assertEquals(r1Size, r2Size, "Rows: " + (i+1) + " have different number of columns!");
            if(r1Size == r2Size)
                for(int y = 0; y < r1Size; y++) {
                    if(!r1.get(y).equals(r2.get(y))) {
                        Assert.assertEquals(r1.get(y), r2.get(y),
                                "Table values are different in row: " + (i+1) + " , column: " + (y+1));
                        return false;
                    }
                }
            else
                return false;
            }

        return true;
    }

    public static boolean compareTables(List<List<String>> t1, ResultSet t2, boolean expectSizeMatch) {
        return compareTables(t1, convertToStringArr(t2), expectSizeMatch);
    }

    public static boolean compareTables(ResultSet t1, ResultSet t2, boolean expectSizeMatch) {
        return compareTables(convertToStringArr(t1), convertToStringArr(t2), expectSizeMatch);
    }

    //We convert ResultSet to an object that is 'easier' to work with
    private static List<List<String>> convertToStringArr(ResultSet r) {
        List<List<String>> r_String = new ArrayList<>();
        int r_Columns = 0;
        try {
            ResultSetMetaData meta = r.getMetaData();
            r_Columns = meta.getColumnCount();

            while(r.next()) {
                List<String> temp = new ArrayList<>();

                for(int i = 1; i <= r_Columns; i++) {
                    String s = String.valueOf(r.getObject(i));
                    if (i == 2)
                        switch (s){
                            case "FRUIT":
                                temp.add(foodType.FRUIT.getFruitNameRus());
                                break;
                            case "VEGETABLE":
                                temp.add(foodType.VEGETABLE.getFruitNameRus());
                                break;
                        }
                    else if (i == 3)
                        switch (s) {
                            case "0":
                                temp.add("false");
                                break;
                            case "1":
                                temp.add("true");
                                break;
                        }
                    else
                        temp.add(s);
                }
                r_String.add(temp);
            }
        }
        catch (SQLException e) { e.printStackTrace(); }

        return r_String;
    }

    public static void compareToLast(List<List<String>> t, List<String> r) {
        Assert.assertNotNull(t, "Empty table provided");
        Assert.assertNotNull(r, "Empty string provided");
        if(t != null && r != null)
            Assert.assertEquals(t.get(t.size() - 1), r, "Rows are not the same!");
    }

    public static void compareToLast(ResultSet t, List<String> r) {
        compareToLast(convertToStringArr(t), r);
    }

}
