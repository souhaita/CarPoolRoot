package rode1lift.ashwin.uomtrust.mu.rod1lift.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DatabaseHelper.DatabaseHelper;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RequestDAO {
    private final String TABLE_NAME = "request";
    private final DatabaseHelper dbHelper;

    public RequestDAO(final Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public RequestDTO getRequestByID(int requestId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE request_id = " + requestId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        RequestDTO dto = new RequestDTO();

        while (!res.isAfterLast()) {

            dto = setRequestDetails(res);

            res.moveToNext();
        }

        res.close();

        return dto;
    }

    public List<RequestDTO> getRequestByStatus(RequestStatus requestStatus) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE request_status = " + requestStatus.getValue());
        sql.append(" ORDER BY request_id DESC");

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        List<RequestDTO> requestDTOList = new ArrayList<>();

        while (!res.isAfterLast()) {

            RequestDTO dto = setRequestDetails(res);

            requestDTOList.add(dto);
            res.moveToNext();
        }

        res.close();

        return requestDTOList;
    }

    private RequestDTO setRequestDetails(Cursor res){
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setRequestId(res.getInt(res.getColumnIndex("request_id")));
        requestDTO.setAccountId(res.getInt(res.getColumnIndex("account_id")));
        requestDTO.setDateUpdated(new Date(res.getLong(res.getColumnIndex("date_updated"))));
        requestDTO.setDateCreated(new Date(res.getLong(res.getColumnIndex("date_created"))));
        requestDTO.setEvenDate(new Date(res.getLong(res.getColumnIndex("event_date"))));
        requestDTO.setPlaceFrom(res.getString(res.getColumnIndex("place_from")));
        requestDTO.setPlaceTo(res.getString(res.getColumnIndex("place_to")));
        requestDTO.setPrice(res.getInt(res.getColumnIndex("price")));
        requestDTO.setSeatAvailable(res.getInt(res.getColumnIndex("seat_available")));
        requestDTO.setRequestStatus(RequestStatus.valueFor(res.getInt(res.getColumnIndex("request_status"))));

        return requestDTO;
    }

    private ContentValues setContentValues(RequestDTO requestDTO){
        ContentValues values = new ContentValues();

        values.put("request_id", requestDTO.getRequestId());
        values.put("account_id", requestDTO.getAccountId());

        if(requestDTO.getDateUpdated() != null)
            values.put("date_updated", requestDTO.getDateUpdated().getTime());

        if(requestDTO.getDateCreated() != null)
            values.put("date_created", requestDTO.getDateCreated().getTime());

        values.put("event_date_time", requestDTO.getEvenDate().getTime());
        values.put("place_from", requestDTO.getPlaceFrom());
        values.put("place_to", requestDTO.getPlaceTo());

        if(requestDTO.getPrice() != null)
            values.put("price", requestDTO.getPrice());

        values.put("request_status", requestDTO.getRequestStatus().getValue());

        return values;

    }

    public long saveOrUpdateRequest(RequestDTO requestDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(requestDTO);

        boolean newRequest = getRequestByID(requestDTO.getRequestId()).getRequestId() == null;

        if(newRequest)
            return db.insert(TABLE_NAME, null, contentValues);
        else
            return db.update(TABLE_NAME, contentValues, "request_id = "+requestDTO.getRequestId(), null);
    }

    public void deleteRequest(Integer requestId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, " request_id = " + requestId, null);
    }

}
