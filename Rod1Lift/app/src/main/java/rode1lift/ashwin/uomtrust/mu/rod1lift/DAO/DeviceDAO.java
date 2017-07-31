package rode1lift.ashwin.uomtrust.mu.rod1lift.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.DeviceDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DatabaseHelper.DatabaseHelper;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class DeviceDAO {
    private final String TABLE_NAME = "device";
    private final DatabaseHelper dbHelper;

    public DeviceDAO(final Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public DeviceDTO getDeviceAccountID(int accountId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE account_id = " + accountId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        DeviceDTO deviceDTO = new DeviceDTO();

        while (!res.isAfterLast()) {

            deviceDTO = setDeviceDetails(res);

            res.moveToNext();
        }

        res.close();

        return deviceDTO;
    }

    public DeviceDTO checkToken(int accountId, String deviceToken) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE account_id = " + accountId);
        sql.append(" and device_token = '" + deviceToken + "'");

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        DeviceDTO deviceDTO = new DeviceDTO();

        while (!res.isAfterLast()) {

            deviceDTO = setDeviceDetails(res);

            res.moveToNext();
        }

        res.close();

        return deviceDTO;
    }


    public DeviceDTO getDeviceByID(int deviceId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE device_id = " + deviceId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        DeviceDTO deviceDTO = new DeviceDTO();

        while (!res.isAfterLast()) {

            deviceDTO = setDeviceDetails(res);

            res.moveToNext();
        }

        res.close();

        return deviceDTO;
    }



    private ContentValues setContentValues(DeviceDTO deviceDTO){
        ContentValues values = new ContentValues();

        values.put("device_id", deviceDTO.getDeviceId());
        values.put("account_id", deviceDTO.getAccountId());
        values.put("device_token", deviceDTO.getDeviceToken());

        return values;

    }

    private DeviceDTO setDeviceDetails (Cursor res){
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setDeviceId(res.getInt(res.getColumnIndex("device_id")));
        deviceDTO.setAccountId(res.getInt(res.getColumnIndex("account_id")));
        deviceDTO.setDeviceToken(res.getString(res.getColumnIndex("device_token")));

        return deviceDTO;
    }

    public long saveOrUpdateDevice(DeviceDTO deviceDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(deviceDTO);

        boolean newDevice = getDeviceByID(deviceDTO.getDeviceId()).getDeviceId() == null;

        if(newDevice)
            return db.insert(TABLE_NAME, null, contentValues);
        else
            return db.update(TABLE_NAME, contentValues, "device_id = "+deviceDTO.getDeviceId(), null);
    }
}
