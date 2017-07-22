package rode1lift.ashwin.uomtrust.mu.rod1lift.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DatabaseHelper.DatabaseHelper;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountStatus;

/**
 * Created by Ashwin on 04-Jul-17.
 */

public class AccountDAO {
    private final String TABLE_NAME = "account";
    private final DatabaseHelper dbHelper;

    public AccountDAO(final Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public AccountDTO getAccountById(final int accountId) {
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM " +TABLE_NAME);
        sql.append(" WHERE account_id = " + accountId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        AccountDTO accountDTO = new AccountDTO();

        while (!res.isAfterLast()) {

            accountDTO = setAccountDetails(res);

            res.moveToNext();
        }

        res.close();

        return accountDTO;
    }

    private AccountDTO setAccountDetails(Cursor res){
        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountId(res.getInt(res.getColumnIndex("account_id")));
        accountDTO.setEmail(res.getString(res.getColumnIndex("email")));
        accountDTO.setProfilePicture(res.getBlob(res.getColumnIndex("profile_picture")));
        accountDTO.setFullName(res.getString(res.getColumnIndex("full_name")));
        accountDTO.setFacebookId(res.getString(res.getColumnIndex("facebook_id")));
        accountDTO.setGoogleId(res.getString(res.getColumnIndex("google_id")));
        accountDTO.setAccountRole(res.getInt(res.getColumnIndex("account_role")) != 0? AccountRole.PASSENGER : AccountRole.DRIVER);
        accountDTO.setAccountStatus(res.getInt(res.getColumnIndex("account_status")) == 0? AccountStatus.ACTIVE : AccountStatus.DESACTIVE);
        accountDTO.setDateCreated(new Date(res.getLong(res.getColumnIndex("date_created"))));
        accountDTO.setDateUpdated(new Date(res.getLong(res.getColumnIndex("date_updated"))));
        accountDTO.setPhoneNum(res.getInt(res.getColumnIndex("phone_num")));

        return accountDTO;
    }

    private ContentValues setContentValues(AccountDTO accountDTO){
        ContentValues values = new ContentValues();

        values.put("account_id", accountDTO.getAccountId());
        values.put("email", accountDTO.getEmail());

        if(accountDTO.getProfilePicture() != null);
            values.put("profile_picture", accountDTO.getProfilePicture());

        values.put("full_name", accountDTO.getFullName());

        if(accountDTO.getFacebookId() != null  && !TextUtils.isEmpty(accountDTO.getFacebookId()))
            values.put("facebook_id", accountDTO.getFacebookId());

        if(accountDTO.getGoogleId() != null  && !TextUtils.isEmpty(accountDTO.getGoogleId()))
            values.put("google_id", accountDTO.getGoogleId());

        if(accountDTO.getAccountRole() != null)
            values.put("account_role", accountDTO.getAccountRole().ordinal());

        if(accountDTO.getAccountStatus() != null)
            values.put("account_status", accountDTO.getAccountStatus().ordinal());

        if(accountDTO.getDateCreated() != null)
            values.put("date_created", accountDTO.getDateCreated().getTime());

        if(accountDTO.getDateUpdated() != null)
            values.put("date_updated", accountDTO.getDateUpdated().getTime());

        if(accountDTO.getPhoneNum() != null)
            values.put("phone_num", accountDTO.getPhoneNum());

        return values;
    }

    public long saveOrUpdateAccount(AccountDTO accountDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(accountDTO);

        boolean accountExist = getAccountById(accountDTO.getAccountId()).getAccountId() == null;

        if(accountExist)
            return db.insert(TABLE_NAME, null, contentValues);
        else
            return db.update(TABLE_NAME, contentValues, "account_id = "+accountDTO.getAccountId(), null);

    }

    public long updateAccountIdFromWS(int accountId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        AccountDTO accountDTO = getAccountById(-1);
        accountDTO.setAccountId(accountId);

        ContentValues contentValues = setContentValues(accountDTO);

        return db.update(TABLE_NAME, contentValues, " account_id = \"" + -1+ "\"" ,null);
    }

}
