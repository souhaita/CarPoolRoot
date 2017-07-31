package rode1lift.ashwin.uomtrust.mu.rod1lift.Constant;

/**
 * Created by Ashwin on 04-Jul-17.
 */

public class CONSTANT {

    public static final String APP_NAME = "rod1Lift";
    public static final String LOGIN = "LOGIN";
    public static final String CURRENT_ACCOUNT_ID = "CURRENT_ACCOUNT_ID";
    public static final String TRIP_PRICE = "price";
    public static final String REQUEST_OBJECT = "requestObject";
    public static final String REQUESTDTO = "requestDTO";

    public static final String CREATE_TRIP_FROM = "from";
    public static final String SEARCH_TRIP_FROM = "from";
    public static final String CREATE_TRIP_TO = "to";
    public static final String SEARCH_TRIP_TO = "to";
    public static final String PROFILE_PICTURE_PATH = "profilePicture";
    public static final String CAR_PICTURE_PATH = "carPictures";
    public static final String ROOT_DIRECTORY = ".rod1Lift";

    public static final int PICK_IMAGE_REQUEST = 1;
    public static final int PERMISSION_CAMERA = 2;
    public static final int CAMERA = 3;
    public static final int CAR_MAKE_ACTIVITY = 4;
    public static final int MAIN_ACTIVITY = 5;
    public static final int PROFILE_ACTIVITY_NAME = 6;
    public static final int PROFILE_ACTIVITY_PHONE_NUMBER = PROFILE_ACTIVITY_NAME;
    public static final int PROFILE_ACTIVITY_PROFILE_PIC = 7;
    public static final int PROFILE_ACTIVITY_PROFILE_CAR_1 = 8;
    public static final int PROFILE_ACTIVITY_PROFILE_CAR_2 = 9;
    public static final int PROFILE_ACTIVITY_PROFILE_CAR_3 = 10;
    public static final int PROFILE_ACTIVITY_PROFILE_CAR_4 = 11;
    public static final int PROFILE_ACTIVITY_PROFILE_CAR_MAKE = 12;
    public static final int PROFILE_ACTIVITY_PROFILE_CAR_YEAR = 13;
    public static final int PROFILE_ACTIVITY_PROFILE_CAR_PLATE_NUM = 14;
    public static final int PROFILE_ACTIVITY_PROFILE_CAR_PASSENGER = 15;

    public static final int CREATE_TRIP_ACTIVITY = 16;
    public static final int MANAGE_TRIP_ACTIVITY_DRIVER_REQUEST_PENDING = 17;
    public static final int PERMISSION_GPS = 18;
    public static final int GOOGLE_SIGN_IN = 19;
    public static final int SEARCH_TRIP_ACTIVITY = 20;
    public static final int PASSENGER_VIEW_TRIP_ACTIVITY = 21;


    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "rod1LiftFirebase";
    public static final String FIREBASE_REGISTRATION_KEY = "firebaseRegistrationKey";
    public static final String FIREBASE_MESSAGE = "firebaseMessage";
    public static final String FIREBASE_TITLE = "firebaseTitle";
}
