package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RevisionDataTest {

    @Test
    public void testCreatable() {
        RevisionData rd = new RevisionData();
        assertTrue(rd.getAuthor().isEmpty());
        assertEquals(-1,rd.getChurn());
        assertEquals(null,rd.getFisheyePath());
    }

    @Test
    public void testSampleInput(){
        String text = "\"Websites/Tetra/trunk/modules/dealer-service/src/main/java/com/cobaltgroup/websites/dealerservice/util/LayoutXmlParser.java\",\"ghoshs\",142,9";
        RevisionData rd = new RevisionData(text);
        assertEquals("[ghoshs]",rd.getAuthor().toString());
        assertEquals(151,rd.getChurn());
        assertEquals("Websites/Tetra/trunk/modules/dealer-service/src/main/java/com/cobaltgroup/websites/dealerservice/util/LayoutXmlParser.java",rd.getFisheyePath());
    }

    @Test
    public void testToString(){
        String text = "\"ui/vaadin-ui/fake.java\",\"blum\",123,321";
        RevisionData rd = new RevisionData(text);
        assertEquals("ui/vaadin-ui/fake.java", rd.getFisheyePath());
        assertEquals("[blum]", rd.getAuthor().toString());
        assertEquals(444,rd.getChurn());
        assertEquals("RevisionData [Fisheyepath=ui/vaadin-ui/fake.java, churn=444, author=[blum]]",rd.toString());
    }

    @Test
    public void testCombiningTwoDissimilarPaths(){
        String text = "\"ui/vaadin-ui/fake.java\",\"blum\",123,321";
        RevisionData rd = new RevisionData(text);
        text = "\"Websites/Tetra/trunk/modules/dealer-service/src/main/java/com/cobaltgroup/websites/dealerservice/util/LayoutXmlParser.java\",\"ghoshs\",142,9";
        RevisionData rd2 = new RevisionData(text);
        assertFalse(rd.combine(rd2));
        assertEquals("ui/vaadin-ui/fake.java", rd.getFisheyePath());
        assertEquals("[blum]", rd.getAuthor().toString());
        assertEquals(444,rd.getChurn());
    }

    @Test
    public void testCombiningTwonulls(){
        RevisionData rd = new RevisionData();
        RevisionData rd2 = new RevisionData();
        assertFalse(rd.combine(rd2));
        assertTrue(rd.getAuthor().isEmpty());
        assertEquals(-1,rd.getChurn());
        assertEquals(null,rd.getFisheyePath());
    }

    @Test
    public void TestTwoArgumentConstructor() {

    }

    @Test
    public void TestCombiningFisheyeDataWithSamePath() {
        String text = "\"ui/vaadin-ui/fake.java\",\"blum\",12,32";
        RevisionData rd1 = new RevisionData(text);
        System.out.println(rd1.toString());
        text = "\"ui/vaadin-ui/fake.java\", \"weiyoud\",10,11";
        RevisionData rd2 = new RevisionData(text);
        RevisionData rd3 = new RevisionData(rd1, rd2);
        System.out.println(rd2.toString());
        text = "\"ui/vaadin-ui/fake.java\", \"author\",13,12";
        // RevisionData rd4 = new RevisionData(text);
        assertTrue(rd1.combine(new RevisionData(text)));
    }


}
