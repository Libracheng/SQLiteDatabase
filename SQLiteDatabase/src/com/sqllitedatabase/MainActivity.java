package com.sqllitedatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import sqliteopenhelper.StuDBHelper;

public class MainActivity extends Activity {
	private EditText editText01;
	private EditText editText02;
	private EditText editText03;

	private StuDBHelper stuDBHelper;
	private SQLiteDatabase database;

	private Button button01;
	private Button button02;
	private Button button03;
	private Button button04;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		stuDBHelper = new StuDBHelper(getApplicationContext());

		database = stuDBHelper.getWritableDatabase();

		editText01 = (EditText) findViewById(R.id.editText01);
		editText02 = (EditText) findViewById(R.id.editText02);
		editText03 = (EditText) findViewById(R.id.editText03);

		button01 = (Button) findViewById(R.id.button01);
		button01.setOnClickListener(new Button01OnClickListener());

		button02 = (Button) findViewById(R.id.button02);
		button02.setOnClickListener(new Button02OnClickListener());

		button03 = (Button) findViewById(R.id.button03);
		button03.setOnClickListener(new Button03OnClickListener());

		button04 = (Button) findViewById(R.id.button04);
		button04.setOnClickListener(new Button04OnClickListener());

	}

	// �������ļ�������
	class Button01OnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			try {

				String ad = editText01.getText().toString().trim();
				String we = editText02.getText().toString().trim();
				String fe = editText03.getText().toString().trim();

				/**
				 * ContentValues������Map�������Map�����ṩ�˴�ȡ���ݶ�Ӧ��put(String key, Xxx
				 * value)��getAsXxx(String key)������
				 * keyΪ�ֶ����ƣ�valueΪ�ֶ�ֵ��Xxxָ���Ǹ��ֳ��õ��������ͣ��磺String��Integer�ȡ�
				 * ContentValues���������ݿ��д�����ݵ��࣬Ҳ�ǲ��õļ�ֵ����������ݵģ�
				 * �е�����content��bundle�ȡ� �ù��캯���ǽ���һ��Ĭ�ϴ�С�Ŀյ����ݼ���
				 */

				ContentValues values = new ContentValues();
				values.put("address", ad);
				values.put("weather", we);
				values.put("feeling", fe);

				database.insert("test", null, values);

				Toast.makeText(MainActivity.this, "��ӳɹ�������", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				if (database!=null) {//ֻ�����ݿⲻΪ�ղſ��Թرգ���Ȼ���׳���ָ���쳣
					database.close();//�ر����ݿ�
				}
			}
		}

	}

	// ����ɾ���ļ�������
	class Button04OnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			try {
				String ad = editText01.getText().toString().trim();
				/**
				 * table��������ɾ�����ݵı����� whereClause�������whereClause�Ӿ�ļ�¼���ᱻɾ����
				 * whereArgs������ΪwhereArgs�Ӿ䴫������� ֱ��ɾ��address����ֵ�ͱ���ad��ֵ��Ӧ��������¼
				 */
				database.delete("test", "address = ?", new String[] { ad });
				Toast.makeText(MainActivity.this, "ɾ���ɹ�������", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				if (database!=null) {
					database.close();
				}
			}
			
		}

	}

	// ������µļ�������
	class Button03OnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			try {
				String ad = editText01.getText().toString().trim();

				ContentValues values = new ContentValues();
				// ��Ÿ��º�address��ֵ
				values.put("address", ad);
				// ��addressֵΪ�����ļ�¼���ĳɺͱ���ad��ȵ�ֵ
				database.update("test", values, "address = ?", new String[] { "����" });
				Toast.makeText(MainActivity.this, "���³ɹ�������", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				if (database!=null) {
					database.close();
				}
			}
			

		}

	}

	// �����ѯ�ļ�������
	class Button02OnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			try {
				String ad = editText01.getText().toString().trim();

				// �ú������ص���һ���α�
				// ����1Ϊ����������2Ϊ���е���������������3ΪҪ��ѯ�����Ӿ䣻����4Ϊ����Ϊselection�Ӿ���ռλ���������ֵ��ֵ�������е�λ����ռλ��������е�λ�ñ���һ�£�����ͻ����쳣��
				Cursor cursor = database.query("test", new String[] { "address", "weather", "feeling" }, "address = ?",
						new String[] { ad }, null, null, null);
				// ����ÿһ����¼
				while (cursor.moveToNext()) {//ѭ�� Cursor ȡ����Ҫ������
					String weather = cursor.getString(cursor.getColumnIndex("weather"));// ��������Ϊweather��ֵ
					String feeling = cursor.getString(cursor.getColumnIndex("feeling"));// ��������Ϊfeeling��ֵ
					Toast.makeText(MainActivity.this, "����:" + weather + "  " + "����:" + feeling, Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				if (database!=null) {
					database.close();
				}
			}
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
