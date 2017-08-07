package rode1lift.ashwin.uomtrust.mu.rod1lift.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DatabaseHelper.DatabaseHelper;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class ManageRequestDAO {
    private final String TABLE_NAME = "manage_request";
    private final DatabaseHelper dbHelper;

    public ManageRequestDAO(final Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public ManageRequestDTO getManageRequest(Integer manageRequestId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE manage_request_id = " + manageRequestId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        ManageRequestDTO manageRequestDTO = new ManageRequestDTO();

        while (!res.isAfterLast()) {
            manageRequestDTO = setDTO(res);
            res.moveToNext();
        }

        res.close();

        return manageRequestDTO;
    }

    private ManageRequestDTO setDTO(Cursor res){
        ManageRequestDTO manageRequestDTO = new ManageRequestDTO();
        manageRequestDTO.setCarId(res.getInt(res.getColumnIndex("car_id")));
        manageRequestDTO.setAccountId(res.getInt(res.getColumnIndex("account_id")));
        manageRequestDTO.setSeatRequested(res.getInt(res.getColumnIndex("seat_requested")));
        manageRequestDTO.setDateCreated(new Date(res.getLong(res.getColumnIndex("date_created"))));
        manageRequestDTO.setDateUpdated(new Date(res.getLong(res.getColumnIndex("date_updated"))));
        manageRequestDTO.setManageRequestId(res.getInt(res.getColumnIndex("manage_request_id")));
        manageRequestDTO.setRequestStatus(RequestStatus.valueFor(res.getInt(res.getColumnIndex("request_status"))));
        manageRequestDTO.setRequestId(res.getInt(res.getColumnIndex("request_id")));

        return manageRequestDTO;
    }


    private ContentValues setContentValues(ManageRequestDTO manageRequestDTO){
        ContentValues values = new ContentValues();

        values.put("car_id", manageRequestDTO.getCarId());
        values.put("account_id", manageRequestDTO.getAccountId());
        values.put("seat_requested", manageRequestDTO.getSeatRequested());
        values.put("date_created", manageRequestDTO.getDateCreated().getTime());
        values.put("date_updated", manageRequestDTO.getDateUpdated().getTime());
        values.put("manage_request_id", manageRequestDTO.getManageRequestId());
        values.put("request_status", manageRequestDTO.getRequestStatus().getValue());
        values.put("request_id", manageRequestDTO.getRequestId());

        return values;

    }

    public void deleteManageRequest(Integer manageRequestId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, " manage_request_id = " + manageRequestId, null);
    }

    public long saveOrUpdateManageRequest(ManageRequestDTO manageRequestDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(manageRequestDTO);

        boolean newRequest = getManageRequest(manageRequestDTO.getManageRequestId()).getRequestId() == null;

        if(newRequest)
            return db.insert(TABLE_NAME, null, contentValues);
        else
            return db.update(TABLE_NAME, contentValues, "manage_request_id = "+manageRequestDTO.getManageRequestId(), null);
    }
}
