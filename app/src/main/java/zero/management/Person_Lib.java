package zero.management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import zero.management.Datababse_Values.Table;

/**
 * Created by Aiy on 2016/12/4.
 */

public class Person_Lib {
    private static Person_Lib personLib;
    private Context context;
    private SQLiteDatabase database;

    public static Person_Lib getPersonLib(Context context){
        if (personLib==null){
            personLib=new Person_Lib(context);
        }
        return personLib;
    }
    private Person_Lib(Context context){
        context=context.getApplicationContext();
        database=new Database_db(context).getWritableDatabase();
    }

    private CursorWrap query(String IF, String[] IF_num){
        Cursor cursor=database.query(
                Table.TABLE_NAME,
                null,
                IF,
                IF_num,
                null,
                null,
                null
        );
        return new CursorWrap(cursor);
    }

    public List<Person> getList(){
        List<Person> list=new ArrayList<>();
        CursorWrap cursor=query(null,null);
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                list.add(cursor.getPerson());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return list;
    }

    public Person getPerson(int num){
        CursorWrap cursor=query(Table.cols.NUM+" = ?",
                new String[] { String.valueOf(num)});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getPerson();
        }finally {
            cursor.close();
        }
    }
    public void Delete(int num){
        database.delete(Table.TABLE_NAME,Table.cols.NUM+"=?",new String[]{String.valueOf(num)});
    }

    private static ContentValues getValuse(Person person){
        ContentValues values=new ContentValues();
        values.put(Table.cols.FROM,person.getFrom());
        values.put(Table.cols.NAME,person.getName());
        values.put(Table.cols.PASSWORD,person.getPassword());
        values.put(Table.cols.NUM,person.getNum());
        values.put(Table.cols.SCORE,person.getScore());
        values.put(Table.cols.SUBJECT,person.getSubject());
        values.put(Table.cols.SEX,person.getSex());
        values.put(Table.cols.UUID,person.getUuid().toString());
        return values;
    }

    public void addPerson(Person person){
        ContentValues values=getValuse(person);
        database.insert(Table.TABLE_NAME,null,values);
    }

    public void updatePerson(Person person){
        ContentValues values=getValuse(person);
        database.update(Table.TABLE_NAME,values,Table.cols.UUID +" = ?"
                ,new String[]{ person.getUuid().toString()});
    }
}
