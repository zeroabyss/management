package zero.management;

import android.database.CursorWrapper;

import java.util.UUID;

import zero.management.Datababse_Values.Table;

/**
 * Created by Aiy on 2016/12/4.
 */

public class CursorWrap extends CursorWrapper {

    public CursorWrap(android.database.Cursor cursor) {
        super(cursor);
    }

    public Person getPerson(){
        int num=getInt(getColumnIndex(Table.cols.NUM));
        String password=getString(getColumnIndex(Table.cols.PASSWORD));
        String from=getString(getColumnIndex(Table.cols.FROM));
        String sex=getString(getColumnIndex(Table.cols.SEX));
        int score=getInt(getColumnIndex(Table.cols.SCORE));
        String subject=getString(getColumnIndex(Table.cols.SUBJECT));
        String name=getString(getColumnIndex(Table.cols.NAME));
        String uuid=getString(getColumnIndex(Table.cols.UUID));

        Person person=new Person(UUID.fromString(uuid));
        person.setNum(num);
        person.setPassword(password);
        person.setFrom(from);
        person.setScore(score);
        person.setName(name);
        person.setSex(sex);
        person.setSubject(subject);
        return person;
    }
}
