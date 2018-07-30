package scut.carson_ho.retrofitdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import scut.carson_ho.retrofitdemo.bean.ResultListBean;

/**
 * Created by Carson_Ho on 17/3/21.
 */
public class PostRequest extends AppCompatActivity {
    /* 设置表相关信息的常量 */
    final String MYTAB = "t_score";
    final String MYNAME ="name";
    final String MYSCORE = "score";
    private TextView tv_get_result,tv_result;
    private EditText ed_question;
    private String queryText;
    private ListView lv_searchlist;
    MySQLiteOpenHelper helper;
    TestActivityAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        initHistory();
    }

    private void intiView() {
  /* 创建一个MySQLiteOpenHelper，该语句执行是不会创建或打开连接的 */
        helper = new MySQLiteOpenHelper(PostRequest.this, "mydb.db", null, 1);
        tv_get_result=(TextView)findViewById(R.id.tv_get_result);
        tv_result=(TextView)findViewById(R.id.tv_result);
        ed_question=(EditText)findViewById(R.id.ed_question);
        lv_searchlist=(ListView)findViewById(R.id.lv_searchlist);
        tv_get_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryText=ed_question.getText().toString();
                if (queryText!=null) {
                    request();
                }
            }
        });
    }

    private void saveHistory(String s) {

        /* 调用MySQLiteOpenHelper的getWriteableDatabase()方法，创建或者打开一个连接 */
        SQLiteDatabase sqlitedatabase = helper.getWritableDatabase();
        Toast.makeText(PostRequest.this, "数据库创建成功", 1000).show();



        /* 创建一个MySQLiteOpenHelper，该语句执行是不会创建或打开连接的 */
        helper = new MySQLiteOpenHelper(PostRequest.this, "mydb.db", null, 1);
        /* 获取一个可写的SQLiteDatabase对象,创建或打开连接 */
        SQLiteDatabase sqliteDatabase3 = helper.getWritableDatabase();
        /* 创建两张表 */
        if (helper.tabIsExist("student")){}else {
            sqliteDatabase3.execSQL("create table student(id INTEGER PRIMARY KEY autoincrement,name text);");
        }
        if (helper.tabIsExist(MYTAB)){}else {
            sqliteDatabase3.execSQL("create table "+MYTAB+"(type text,"+MYNAME+" text,"+MYSCORE+" text);");
        }
        /* 小贴士 */
        Toast.makeText(PostRequest.this, "数据表创建成功", 1000).show();


        /* 连接数据库 *//* 数据库中有表 ， 对表进行操作 */
        SQLiteDatabase sqliteDatabase = helper.getWritableDatabase();
        /* 给表添加数据： *//* 方式1： *//* 增加一条数据 */
        sqliteDatabase.execSQL("insert into student(name) values('mike')");
        /* 方式2： *//* 使用SQLiteDatabase 对象的insert()方法 */
        /* 创建ContentValues对象 *//* 每次插入的时一条数据 */
        ContentValues cv = new ContentValues();
        cv.put("name", "mary"); /* key==列 value==值 */
        sqliteDatabase.insert("student", null, cv);
        cv.clear();
        /* 对MYTAB进行数据添加 */
        //sqliteDatabase.execSQL("insert into "+MYTAB+" values('xf','ray',95)");
        try {
            sqliteDatabase.execSQL("insert into " + MYTAB + " values('xf','" + s + "','" + queryText + "')");
        }catch (Exception e){

        }
        cv.clear();
        Toast.makeText(PostRequest.this, "数据插入成功", 1000).show();



        /* 建立和数据库的连接，获取SQLiteDatabase对象 */
        SQLiteDatabase sqliteDatabase4 = helper.getWritableDatabase();
        /* 方式1：直接使用语句 */
       //sqliteDatabase.execSQL("update student set name='mary key' where id=1");
        /* 方式2：使用sqliteDatabase.update();方法 */
        ContentValues cv1 = new ContentValues();
        cv1.put("name", "mary key"); /* 确定需要修改对应列的值 */
        /* 参1：表名 ; 参2：ContentValues对象; 参3：where字句，相当于sql中where后面的语句,?是占位符 */
        /* 参4：占位符的值; */
        sqliteDatabase4.update("student", cv1, "id=?", new String[]{"1"});
        Toast.makeText(PostRequest.this, "数据修改成功", 1000).show();



        /* 获取SQLiteDatabase的对象 */
        SQLiteDatabase sqliteDatabase5 = helper.getReadableDatabase();
        /* 调用SQLiteDatabase的query()方法进行查询，返回一个Cursor对象：由数据库查询返回的结果集对象 */
        /* 参1 String：表名

　　  * 参2 String[]:需要查询的列;
　　  * 参3 String :查询条件;
　　  * 参4 String[]:查询条件的参数;
　　* 参5 String: 对查询的结果进行分组;
　　* 参6 String: 对分组结果进行限制;
　　* 参7 String: 对查询结果进行排序;
　　* */
        Cursor cursor = sqliteDatabase5.query("t_score", new String[]{"name","score"}, "type=?", new String[]{"xf"}, null, null, null);
        /* 保存结果集中对应字段的数据 */
        String id = null;
        String name = null;
        /* 从结果集中读取数据 */
        ArrayList<ResultListBean> list=new ArrayList<>();
        while(cursor.moveToNext()){
            ResultListBean bean=new ResultListBean();
            id = cursor.getString(cursor.getColumnIndex("name"));
            name = cursor.getString(cursor.getColumnIndex("score"));
            bean.name=name;
            bean.score=id;
            list.add(bean);

        }
        myAdapter.update(list);
        Toast.makeText(PostRequest.this, "查询数据为：id="+id+" \n name="+name, 1000).show();



    }

    public void initHistory(){
        SQLiteDatabase sqliteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqliteDatabase.query("t_score", new String[]{"name","score"}, "type=?", new String[]{"xf"}, null, null, null);
        int i=cursor.getCount();
        if (i>30){

        }
        /* 保存结果集中对应字段的数据 */
        String id = null;
        String name = null;
        /* 从结果集中读取数据 */
        ArrayList<ResultListBean> list=new ArrayList<>();
        while(cursor.moveToNext()){
            ResultListBean bean=new ResultListBean();
            id = cursor.getString(cursor.getColumnIndex("name"));
            name = cursor.getString(cursor.getColumnIndex("score"));
            bean.name=name;
            bean.score=id;
            list.add(bean);

        }
        myAdapter=new TestActivityAdapter(getBaseContext(),list);
        lv_searchlist.setAdapter(myAdapter);
    }
    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<Translation1> call = request.getCall(queryText);

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation1>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                // 请求处理,输出结果
                // 输出翻译的内容
                System.out.println("翻译是："+ response.body().getTranslateResult().get(0).get(0).getTgt());
                tv_result.setText(response.body().getTranslateResult().get(0).get(0).getTgt());
                saveHistory(response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation1> call, Throwable throwable) {
                System.out.println("请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }


}