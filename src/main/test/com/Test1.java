package com;

import com.example01.SimpleExample;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Test1 {

    private static Logger logger = LogManager.getLogger("analysis");

    @org.junit.Test
    public void test() {
        logger.error("ok");
    }
}
