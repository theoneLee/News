package News.util;

/**
 * Created by Lee on 2017/6/3 0003.
 */
public class PermissionUtil {
    private static final String PERMISSION="NOTADMIN";

    public static boolean isPermission(String permission){
        if (permission!=null && permission.equals(PERMISSION)){
            return true;
        }
        return false;
    }
}
