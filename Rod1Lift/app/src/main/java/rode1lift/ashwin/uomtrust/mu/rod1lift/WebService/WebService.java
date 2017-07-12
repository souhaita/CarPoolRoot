package rode1lift.ashwin.uomtrust.mu.rod1lift.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class WebService {

    //COMMON

    //private static final String SERVER_URL = "http://192.168.100.3:8080/api/";
    private static final String SERVER_URL = "http://192.168.20.59:8080/api/";
    public static final String API_ACCOUNT = SERVER_URL + "account/";
    public static final String API_CLIENT = SERVER_URL + "client/";
    public static final String API_REQUEST = SERVER_URL + "request/";

    public static final String API_CREATE_ACCOUNT = API_ACCOUNT +"createAccount";

    public static final String API_CHECK_ACCOUNT_VIA_EMAIL = API_ACCOUNT +"checkAccountViaEmail";


    //USER
    public static final String USER_API_DELETE_REQUEST = API_REQUEST +"userDeleteRequest";
    public static final String USER_API_GET_PENDING_REQUEST_LIST = API_REQUEST +"userGetPendingRequestList";
    public static final String USER_API_OTHER_REQUEST_LIST = API_REQUEST +"userGetOtherRequestList";
    public static final String USER_API_ACCEPT_OR_REJECT_REQUEST = API_REQUEST +"acceptOrRejectRequestUser";


    //DRIVER
    public static final String DRIVER_API_CREATE_CAR_DETAILS = API_ACCOUNT +"driverCreateCar";
    public static final String DRIVER_API_CREATE_UPDATE_REQUEST = API_REQUEST +"driverCreateUpdateRequest";
    public static final String DRIVER_API_GET_PENDING_REQUEST_LIST = API_REQUEST +"driverGetPendingRequestList";

    public static final String DRIVER_API_OTHER_REQUEST_LIST = API_REQUEST +"driverGetOtherRequestList";
    public static final String TAXI_API_GET_CAR_DETAILS = API_CLIENT +"taxiGetCarDetails";
    public static final String TAXI_API_ACCEPT_OR_REJECT_REQUEST = API_REQUEST +"acceptOrRejectRequestTaxi";



}
