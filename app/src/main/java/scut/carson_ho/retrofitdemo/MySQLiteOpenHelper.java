package scut.carson_ho.retrofitdemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 圆子弹 on 2018/3/14.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void onCreate(SQLiteDatabase db) {
    // TODO 创建数据库后，对数据库的操作

    }
    /* 将数据库从旧的模型转换为新的模型 *//* 参1：对象 ; 参2：旧版本号 ; 参3：新版本号 */

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // TODO 更改数据库版本的操作
    }
    /* 打开数据库执行的函数 */

    public void onOpen(SQLiteDatabase db) {
    // TODO 每次成功打开数据库后首先被执行
        super.onOpen(db);
    }


    /**
     * 判断某张表是否存在
     * @param tabName 表名
     * @return
     */
    public  boolean tabIsExist(String tabName){
        boolean result = false;
        if(tabName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();//此this是继承SQLiteOpenHelper类得到的
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tabName.trim()+"'" ;
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

}
