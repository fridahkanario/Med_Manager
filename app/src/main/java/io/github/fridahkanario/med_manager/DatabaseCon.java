package io.github.fridahkanario.med_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.fridahkanario.med_manager.Models.Medicine;


public class DatabaseCon extends SQLiteOpenHelper {
    private static final  String DB_NAME ="meds";
    private static final  String  TABLE_NAME ="medication";
    private static final  int DB_VERSION= 1;
    SQLiteDatabase sqLiteDatabase;

   public DatabaseCon(Context context){
       super(context, DB_NAME , null, DB_VERSION);
   }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "  CREATE TABLE  " + TABLE_NAME + "( medId integer not null PRIMARY KEY AUTOINCREMENT , medName TEXT, medDescription TEXT, medPrescription TEXT, startDate TEXT )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addNewMedication(Medicine medicine){

       sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("medName", medicine.getMedName());
        values.put("medDescription", medicine.getMedDesc());
        values.put("medPrescription", medicine.getMedPrescription());
        values.put("startDate", medicine.getStartDate());

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public Cursor getALLMedication(){

        sqLiteDatabase = this.getReadableDatabase();

        String select_all = "selct * from "+ TABLE_NAME ;
        String [] columns = {"medId", "medDescription", "medPrescription", "startDate"};

       // Cursor cursor = sqLiteDatabase.rawQuery(select_all,null);
        return  sqLiteDatabase.query(TABLE_NAME, columns, null, null, null,null, null);


        //sqLiteDatabase.close();
    }
}
