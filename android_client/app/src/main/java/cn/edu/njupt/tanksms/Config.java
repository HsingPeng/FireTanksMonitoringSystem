package cn.edu.njupt.tanksms;

public class Config {

    //sharedpreference默认存储位置
    public static final String SHARED_PREF = "default";

    //标识
    public static final String SUCCESS = "success";
    public static final String FAILURE = "fail";
    public static final String CODE_302 = "302";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String UID = "uid";
    public static final String UNAME = "uname";
    public static final String REMARK = "remark";
    public static final String SERVER_ADDRESS = "server";

    //broadcast接收器
    public static final String ALL_ACTIVITY_RECEIVER = "cn.edu.njupt.tanksms.ALL";
    public static final String WELCOME_ACTIVITY_RECEIVER = "cn.edu.njupt.tanksms.WelcomeActivity";
    public static final String LOGIN_ACTIVITY_RECEIVER = "cn.edu.njupt.tanksms.LoginActivity";
    public static final String MAIN_ACTIVITY_RECEIVER = "cn.edu.njupt.tanksms.MainActivity";
    public static final String DETAIL_ACTIVITY_RECEIVER = "cn.edu.njupt.tanksms.DetailActivity";
    public static final String LOG_ACTIVITY_RECEIVER = "cn.edu.njupt.tanksms.LogActivity";
    public static final String HELP_ACTIVITY_RECEIVER = "cn.edu.njupt.tanksms.HelpActivity";

    //缺省ACTION=0 请勿使用
    public static final int ACTION_DEFAULT = 0;

    public static final int ACTION_LOGIN = 6000;
    public static final int ACTION_WELCOME = 6001;
    public static final int ACTION_GET_CURRENT = 6002;
    public static final int ACTION_GET_DETAILS = 6003;
    public static final int ACTION_GET_LOG = 6004;
    public static final int ACTION_GET_HELP = 6005;
    public static final int ACTION_LOGOUT = 6006;
    public static final int ACTION_STOP_SERVICE = 6100;

    public static final String ACTION_FLAG = "flag";

    public static final String TASK_FLAG = "result";
    public static final String TASK_DATA = "data";

    //线程池大小
    public static final int THREAD_POOL_SIZE = 1;
    //主页面刷新周期
    public static final long REFRESH_PERIOD = 5000L;

    //http连接超时设置
    public static int CONNECT_TIME_OUT = 5000;
    public static int CONNECT_READ_TIME_OUT = 5000;

    //全局debug显示log开关
    public static boolean IS_DEBUG = true ;

    //服务器默认地址
    public static String URL = "http://192.168.1.103/TanksMS/" ;
    public static String URL_GET_CURRENT = "getCurrent.php/";
    public static String URL_LOGIN = "LoginControl.php/";
    public static String URL_LOGOUT = "LogoutControl.php/";
    public static String URL_DETAILS = "ManageDetail.php";
    public static String URL_LOG = "ManageLog.php";
    public static String URL_HELP = "ManageHelp.php";

    //记录当前用户信息
    private static int current_uid;
    private static String current_uname;
    private static String current_remark;

}
