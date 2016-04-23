package sqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//getReadableDatabase()和getWriteableDatabase()可以获得SQLiteDatabase对象
public class StuDBHelper extends SQLiteOpenHelper {
	private static String name = "mydb.db";//表示数据库的名称
	 private static final int version = 1;//表示数据库的版本号码

	 //必须要有构造函数  
	      /*
	       * 参数介绍：context 程序上下文环境 即：XXXActivity.this 
	       * name 数据库名字 
	       * factory 接收数据，一般情况为null
	       * version 数据库版本号
	       */
	public StuDBHelper(Context context) {
		super(context, name, null, version);
	}

	// 当第一次创建数据库的时候，调用该方法
	@Override
	public void onCreate(SQLiteDatabase database) {
		//数据库表的创建
		String sql = "create table test (address varchar(64),weather varchar(20),feeling varchar(64))";
		//execSQL函数用于执行SQL语句  
		database.execSQL(sql);
	}

    //当更新数据库的时候执行该方法  。数据库版本变化时，会调用onUpgrade()
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
