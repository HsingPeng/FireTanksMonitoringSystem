package cn.edu.njupt.tanksms;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test1(){
        int position = Config.URL.indexOf("/",7);
        String domain = Config.URL.substring(0,position);
        System.out.println(domain);
    }

}