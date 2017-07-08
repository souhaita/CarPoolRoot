package rode1lift.ashwin.uomtrust.mu.rod1lift.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DatabaseHelper.DatabaseHelper;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class CarDAO {
    private final String TABLE_NAME = "car";
    private final DatabaseHelper dbHelper;

    public CarDAO(final Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public CarDTO getCarByAccountID(int accountId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE account_id = " + accountId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        CarDTO carDTO = new CarDTO();

        while (!res.isAfterLast()) {

            carDTO = setCarDetails(res);

            res.moveToNext();
        }

        res.close();

        return carDTO;
    }

    public CarDTO getCarByCarID(int carId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE car_id = " + carId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        CarDTO carDTO = new CarDTO();

        while (!res.isAfterLast()) {

            carDTO = setCarDetails(res);

            res.moveToNext();
        }

        res.close();

        return carDTO;
    }

    private ContentValues setContentValues(CarDTO carDTO){
        ContentValues values = new ContentValues();

        values.put("car_id", carDTO.getCarId());
        values.put("account_id", carDTO.getAccountId());
        values.put("make", carDTO.getMake());
        values.put("model", carDTO.getModel());
        values.put("year", carDTO.getYear());
        values.put("num_of_passenger", carDTO.getNumOfPassenger());

        if(carDTO.getPicture1() != null)
            values.put("picture1", carDTO.getPicture1());

        if(carDTO.getPicture2() != null)
            values.put("picture2", carDTO.getPicture2());

        if(carDTO.getPicture3() != null)
            values.put("picture3", carDTO.getPicture3());

        if(carDTO.getPicture4() != null)
            values.put("picture4", carDTO.getPicture4());

        values.put("hasPic1", carDTO.isHasPic1());
        values.put("hasPic2", carDTO.isHasPic2());
        values.put("hasPic3", carDTO.isHasPic3());
        values.put("hasPic4", carDTO.isHasPic4());

        values.put("plate_num", carDTO.getPlateNum());

        return values;

    }

    private CarDTO setCarDetails (Cursor res){
        CarDTO carDTO = new CarDTO();

        carDTO.setCarId(res.getInt(res.getColumnIndex("car_id")));
        carDTO.setAccountId(res.getInt(res.getColumnIndex("account_id")));
        carDTO.setMake(res.getString(res.getColumnIndex("make")));
        carDTO.setModel(res.getString(res.getColumnIndex("model")));
        carDTO.setYear(res.getInt(res.getColumnIndex("year")));
        carDTO.setNumOfPassenger(res.getInt(res.getColumnIndex("num_of_passenger")));
        carDTO.setPlateNum(res.getString(res.getColumnIndex("plate_num")));

        carDTO.setPicture1(res.getBlob(res.getColumnIndex("picture1")));
        carDTO.setPicture2(res.getBlob(res.getColumnIndex("picture2")));
        carDTO.setPicture3(res.getBlob(res.getColumnIndex("picture3")));
        carDTO.setPicture4(res.getBlob(res.getColumnIndex("picture4")));

        carDTO.setHasPic1(res.getInt(res.getColumnIndex("hasPic1")) == 0? false:true);
        carDTO.setHasPic2(res.getInt(res.getColumnIndex("hasPic2")) == 0? false:true);
        carDTO.setHasPic3(res.getInt(res.getColumnIndex("hasPic3")) == 0? false:true);
        carDTO.setHasPic4(res.getInt(res.getColumnIndex("hasPic4")) == 0? false:true);

        return carDTO;
    }

    public long saveOrUpdateCar(CarDTO carDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(carDTO);

        boolean newCar = getCarByCarID(carDTO.getCarId()).getCarId() == null;

        if(newCar)
            return db.insert(TABLE_NAME, null, contentValues);
        else
            return db.update(TABLE_NAME, contentValues, "car_id = "+carDTO.getCarId(), null);

    }

    public long updateCarIdFromWS(int carId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CarDTO carDetailsDTO = getCarByCarID(-1);
        carDetailsDTO.setCarId(carId);

        ContentValues contentValues = setContentValues(carDetailsDTO);

        return db.update(TABLE_NAME, contentValues, " car_id = \"" + -1 + "\"" ,null);
    }
}
