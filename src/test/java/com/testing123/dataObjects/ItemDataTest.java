package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.dataObjects.ItemData;

public class ItemDataTest {
    private static Object[] itemList = new Object[3];


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        itemList[0] = "String";
        itemList[1] = 6;
        itemList[2] = 0.0;
    }

    @Test
    public void testSetAndGetItem1() {
        ItemData item = new ItemData();
        item.setItem(itemList);
        assertEquals("String", item.getItem(0));
    }

    @Test
    public void testSetAndGetItem2() {
        ItemData item = new ItemData();
        item.setItem(itemList);
        assertEquals(6, item.getItem(1));
    }

    @Test
    public void testSetAndGetItem3() {
        ItemData item = new ItemData();
        item.setItem(itemList);
        assertEquals(0.0, item.getItem(2));
    }

}
