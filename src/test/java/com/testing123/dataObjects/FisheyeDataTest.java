package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class FisheyeDataTest {
    private static ItemData item1;

    private static ItemData item2;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        item1 = new ItemData();
        Object[] obj1 = new Object[3];
        obj1[0] = "name";
        obj1[1] = 1.0;
        obj1[2] = 2;
        item2 = new ItemData();
        Object[] obj2 = new Object[3];
        obj2[0] = "key";
        obj2[1] = 2.0;
        obj2[2] = 1;
        item1.setItem(obj1);
        item2.setItem(obj2);
    }

    @Test
    public void TestSetAndGetRow() {
        FisheyeData fd = new FisheyeData();
        List<ItemData> list = new ArrayList<ItemData>();
        list.add(item1);
        list.add(item2);
        fd.setRow(list);
        assertEquals("name", fd.getRow().get(0).getItem(0));
        assertEquals(1.0, fd.getRow().get(0).getItem(1));
        assertEquals(2, fd.getRow().get(0).getItem(2));
        assertEquals("key", fd.getRow().get(1).getItem(0));
        assertEquals(2.0, fd.getRow().get(1).getItem(1));
        assertEquals(1, fd.getRow().get(1).getItem(2));

        // assertEquals("[key, 2.0, 1]", fd.getRow().get(1));
    }

    @Test
    public void TestSetAndGetHeadings() {
        FisheyeData fd = new FisheyeData();
        List<String> list = new ArrayList<String>();
        list.add("name");
        list.add("file");
        list.add("author");
        fd.setHeadings(list);
        assertEquals("[name, file, author]", fd.getHeadings().toString());

    }

}
