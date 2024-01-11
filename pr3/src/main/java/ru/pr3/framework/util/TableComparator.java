package ru.pr3.framework.util;

import org.testng.Assert;
import java.util.List;

public class TableComparator {
    public boolean tablesEqual(List<List<String>> table1, List<List<String>> table2) {
        int t1Size = table1.size();
        int t2Size = table2.size();

        if(t1Size != t2Size) {
            Assert.assertEquals(t1Size, t2Size, "Tables are not equal!");
            return false;
        }
        else if(t1Size != 0){
            for(int i = 0; i < t1Size; i++) {
                List<String> r1 = table1.get(i);
                List<String> r2 = table1.get(i);
                int r1Size = r1.size();
                int r2Size = r2.size();
                Assert.assertEquals(r1Size, r2Size, "Rows#: " + i + " have different structures!");
                if(r1Size == r2Size)
                    for(int y = 0; y < r1Size; y++) {
                        if(!r1.get(y).equals(r2.get(y)))
                            return false;
                    }
            }
        }

        return true;
    }

    public boolean previousUnchanged(List<List<String>> oldTable, List<List<String>> newTable) {
        int t1Size = oldTable.size();
        int t2Size = newTable.size();
        if(t1Size != 0 && t2Size != 0 && t1Size <= t2Size) {
            for(int i = 0; i < t1Size; i++) {
                List<String> r1 = oldTable.get(i);
                List<String> r2 = newTable.get(i);
                int r1Size = r1.size();
                int r2Size = r2.size();
                Assert.assertEquals(r1Size, r2Size, "Rows#: " + i + " have different structures!");
                if(r1Size == r2Size)
                    for(int y = 0; y < r1Size; y++)
                        if(!r1.get(y).equals(r2.get(y)))
                            return false;

            }
        }
        else
            return false;

        return true;
    }
}
