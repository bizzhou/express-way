package com.expressway;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;
import com.expressway.util.Helper;




@RunWith(SpringRunner.class)
public class HelperTest {

    Helper helper = new Helper();


    @Test
    public void mockTest() {

        Assert.assertEquals(new Integer(331), helper.integerId("{last_itemid=331)"));
        Assert.assertEquals(new Integer(0), helper.integerId("{last_itemid=0)"));
        Assert.assertEquals(new Integer(999), helper.integerId("{last_itemid=999)"));
        Assert.assertEquals(new Integer(999999), helper.integerId("{last_itemid=999999)"));

    }


}
