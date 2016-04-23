package sqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//getReadableDatabase()��getWriteableDatabase()���Ի��SQLiteDatabase����
public class StuDBHelper extends SQLiteOpenHelper {
	private static String name = "mydb.db";//��ʾ���ݿ������
	 private static final int version = 1;//��ʾ���ݿ�İ汾����

	 //����Ҫ�й��캯��  
	      /*
	       * �������ܣ�context ���������Ļ��� ����XXXActivity.this 
	       * name ���ݿ����� 
	       * factory �������ݣ�һ�����Ϊnull
	       * version ���ݿ�汾��
	       */
	public StuDBHelper(Context context) {
		super(context, name, null, version);
	}

	// ����һ�δ������ݿ��ʱ�򣬵��ø÷���
	@Override
	public void onCreate(SQLiteDatabase database) {
		//���ݿ��Ĵ���
		String sql = "create table test (address varchar(64),weather varchar(20),feeling varchar(64))";
		//execSQL��������ִ��SQL���  
		database.execSQL(sql);
	}

    //���������ݿ��ʱ��ִ�и÷���  �����ݿ�汾�仯ʱ�������onUpgrade()
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
