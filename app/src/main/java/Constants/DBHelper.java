package Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public DBHelper(Context context) {
        super(context, "networkit", null, 1);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table favorite(group_id text primary key, name text,title text,address text,rating text,image text, isfavt text,bussiness text,organizer text,detail text,meeting_days text,meeting_time text);");
        db.execSQL("create table events(event_id text primary key, name text,title text,address text,image text,organizer text,detail text,date text,time text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS favorite");
        db.execSQL("DROP TABLE IF EXISTS events");

        // Create tables again
        onCreate(db);

    }

    public boolean insert(String group_id, String name, String title, String address, String rating, String image, String isfavt, String bussiness, String organizer, String detail, String meeting_days, String meeting_time) {

        SQLiteDatabase db = this.getWritableDatabase();  //to check wheather  database created or not


        //to insert column name in the columns
        ContentValues content = new ContentValues();

        content.put("name", name);
        content.put("title", title);
        content.put("address", address);
        content.put("rating", rating);
        content.put("image", image);
        content.put("group_id", group_id);
        content.put("isfavt", isfavt);
        content.put("bussiness", bussiness);
        content.put("organizer", organizer);
        content.put("detail", detail);
        content.put("meeting_days", meeting_days);
        content.put("meeting_time", meeting_time);


        long result = db.insert("favorite", null, content);
        if (result == -1)
            return false;
        else
            return true;


    }


    public boolean insetEventData(String event_id, String name, String title, String address, String image, String organizer, String detail, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();  //to check wheather  database created or not

        //to insert column name in the columns
        ContentValues content = new ContentValues();

        content.put("name", name);
        content.put("title", title);
        content.put("address", address);
        content.put("image", image);
        content.put("event_id", event_id);
        content.put("organizer", organizer);
        content.put("detail", detail);
        content.put("date", date);
        content.put("time", time);


        long result = db.insert("events", null, content);
        if (result == -1)
            return false;
        else
            return true;

    }


    public Boolean logincheck(String phone, String password) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery("select id from users where phone='" + phone
                    + "' and password='" + password + "'", null);

            if (cursor != null && cursor.getCount() > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean deleteFavt(String favt) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorite", "group_id" + "=" + favt, null) > 0;
    }

    public Cursor getData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from favorite", null);
        return res;

    }


    public Cursor getEventData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from events", null);
        return res;

    }

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("orders", null,
                null);
        db.close();
    }

    public void update(String group_id, String favt) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE favorite SET isfavt = '" + favt + "' WHERE group_id='" + group_id + "'");
        Log.d("Update", "updatedddd");
        db.close();
    }

    public Cursor getFavtData(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from favorite where group_id=" + id, null);
        return res;

    }

    public boolean checkData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from favorite where group_id=" + id, null);
        if (res.getCount() <= 0) {
            res.close();
            return false;
        } else {
            return true;
        }


    }


    public boolean checkEventData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from events where event_id=" + id, null);
        if (res.getCount() <= 0) {
            res.close();
            return false;
        } else {
            return true;
        }


    }

    public String checkFavt(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from favorite where group_id=" + id, null);
        String favt = null;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            favt = cursor.getString(6);
            cursor.close();
        }
        return favt;
    }

}
