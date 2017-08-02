package rode1lift.ashwin.uomtrust.mu.rod1lift.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.MessageDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DatabaseHelper.DatabaseHelper;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class MessageDAO {
    private final String TABLE_NAME = "message";
    private final DatabaseHelper dbHelper;
    private Context context;

    public MessageDAO(final Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
        this.context = context;
    }

    public List<MessageDTO> getMessageList() {
        int accountId = Utils.getCurrentAccount(context);

        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE account_id = " + accountId);
        sql.append(" group by other_user_id");
        sql.append(" order by message_id desc");

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        List<MessageDTO> messageDTOList = new ArrayList<>();

        while (!res.isAfterLast()) {
            MessageDTO messageDTO = setMessageDetails(res);
            messageDTOList.add(messageDTO);
            res.moveToNext();
        }

        res.close();

        return messageDTOList;
    }

    public MessageDTO getMessageById(int chatId) {
        int accountId = Utils.getCurrentAccount(context);

        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE account_id = " + accountId);
        sql.append(" AND message_id = " + chatId);

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        MessageDTO messageDTO = new MessageDTO();

        while (!res.isAfterLast()) {
            messageDTO = setMessageDetails(res);
            res.moveToNext();
        }

        res.close();

        return messageDTO;
    }

    public List<MessageDTO> getMessageByOtherUserId(int otherUserId) {
        int accountId = Utils.getCurrentAccount(context);

        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM "+ TABLE_NAME);
        sql.append(" WHERE other_user_id = " + otherUserId);
        sql.append(" AND account_id = " + accountId);
        sql.append(" ORDER BY message_id asc");

        dbHelper.open();
        Cursor res = dbHelper.executeQuery(sql.toString(), null);
        if (res != null) {
            res.moveToFirst();
        }

        List<MessageDTO> messageDTOList = new ArrayList<>();

        while (!res.isAfterLast()) {
            MessageDTO messageDTO = setMessageDetails(res);
            messageDTOList.add(messageDTO);

            res.moveToNext();
        }

        res.close();

        return messageDTOList;
    }


    private ContentValues setContentValues(MessageDTO messageDTO){
        ContentValues values = new ContentValues();

        values.put("message_id", messageDTO.getMessageId());
        values.put("account_id", messageDTO.getAccountId());
        values.put("other_user_id", messageDTO.getOtherUserId());
        values.put("message", messageDTO.getMessage());
        values.put("from_user", messageDTO.isFromUser());

        return values;

    }

    private MessageDTO setMessageDetails(Cursor res){
        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setAccountId(res.getInt(res.getColumnIndex("account_id")));
        messageDTO.setMessage(res.getString(res.getColumnIndex("message")));
        messageDTO.setOtherUserId(res.getInt(res.getColumnIndex("other_user_id")));
        messageDTO.setMessageId(res.getInt(res.getColumnIndex("message_id")));
        messageDTO.setFromUser(res.getInt(res.getColumnIndex("from_user")) == 0? false:true);

        return messageDTO;
    }

    public long saveorUpdate(MessageDTO messageDTO){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = setContentValues(messageDTO);

        boolean newMessage = getMessageById(messageDTO.getMessageId()).getMessageId() == null;

        if(newMessage)
            return db.insert(TABLE_NAME, null, contentValues);
        else
            return db.update(TABLE_NAME, contentValues, "message_id = "+ messageDTO.getMessageId(), null);
    }
}
