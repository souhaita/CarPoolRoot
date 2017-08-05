package rode1lift.ashwin.uomtrust.mu.rod1lift.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class WebService {

    //COMMON

    private static final String SERVER_URL = "http://192.168.100.9:8080/api/";
//    private static final String SERVER_URL = "http://192.168.20.59:8080/api/";
    public static final String API_ACCOUNT = SERVER_URL + "account/";
    public static final String API_REQUEST = SERVER_URL + "request/";
    public static final String API_CREATE_ACCOUNT = API_ACCOUNT +"createAccount";
    public static final String API_CHECK_ACCOUNT_VIA_EMAIL = API_ACCOUNT +"checkAccountViaEmail";
    public static final String API_SEND_MESSAGE = API_ACCOUNT +"saveMessage";
    public static final String API_DOWNLOAD_MESSAGE = API_ACCOUNT +"downloadMessage";
    public static final String API_SAVE_DEVICE_TOKEN = API_ACCOUNT +"saveDeviceToken";


    //CAR SEEKER
    public static final String API_PASSENGER_GET_NEW_REQUEST_LIST = API_REQUEST +"passengerGetNewList";
    public static final String API_PASSENGER_GET_PENDING_LIST = API_REQUEST +"passengerGetPendingList";
    public static final String API_PASSENGER_DELETE_REQUEST = API_REQUEST +"passengerDeleteRequest";
    public static final String API_PASSENGER_ACCEPT_REQUEST = API_REQUEST +"passengerAcceptRequest";
    public static final String API_PASSENGER_PAY_REQUEST = API_REQUEST +"passengerPayRequest";
    public static final String API_PASSENGER_GET_ACCEPTED_LIST = API_REQUEST +"passengerGetAcceptedRequest";
    public static final String API_PASSENGER_GET_HISTORY_LIST = API_REQUEST +"passengerGetHistoryList";


    //CAR POOLER
    public static final String API_DRIVER_CREATE_CAR_DETAILS = API_ACCOUNT +"driverCreateCar";
    public static final String API_DRIVER_CREATE_UPDATE_REQUEST = API_REQUEST +"driverCreateUpdateRequest";
    public static final String API_DRIVER_GET_PENDING_REQUEST_LIST = API_REQUEST +"driverGetPendingRequestList";
    public static final String API_DRIVER_FETCH_CAR_DETAILS = API_ACCOUNT +"driverFetchCarDetails";
    public static final String API_DRIVER_DELETE_PENDING_REQUEST = API_REQUEST +"driverDeletePendingRequest";
    public static final String API_DRIVER_DELETE_CLIENT_REQUEST = API_REQUEST +"driverDeleteClientRequest";
    public static final String API_DRIVER_GET_USER_ACCEPTED_REQUEST_LIST = API_REQUEST +"driverGetUserAcceptedRequestList";
    public static final String API_DRIVER_ACCEPT_CLIENT_REQUEST = API_REQUEST +"driverAcceptClientRequest";
    public static final String API_DRIVER_GET_HISTORY_LIST = API_REQUEST +"driverGetHistoryList";


}
