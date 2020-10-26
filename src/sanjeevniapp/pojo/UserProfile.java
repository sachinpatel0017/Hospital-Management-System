
package sanjeevniapp.pojo;


public class UserProfile {
    private static String username;
    private static String usertype;
    private static String uid;

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        UserProfile.uid = uid;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserProfile.username = username;
    }

    public static String getUsertype() {
        return usertype;
    }

    public static void setUsertype(String usertype) {
        UserProfile.usertype = usertype;
    }
    
}
