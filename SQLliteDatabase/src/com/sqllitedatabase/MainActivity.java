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

	// 定义插入的监听器类
	class Button01OnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			try {

				String ad = editText01.getText().toString().trim();
				String we = editText02.getText().toString().trim();
				String fe = editText03.getText().toString().trim();

				/**
				 * ContentValues类似于Map，相对于Map，它提供了存取数据对应的put(String key, Xxx
				 * value)和getAsXxx(String key)方法，
				 * key为字段名称，value为字段值，Xxx指的是各种常用的数据类型，如：String、Integer等。
				 * ContentValues是用于数据库中存放数据的类，也是采用的键值对来存放数据的，
				 * 有点类似content和bundle等。 该构造函数是建立一个默认大小的空的数据集。
				 */

				ContentValues values = new ContentValues();
				values.put("address", ad);
				values.put("weather", we);
				values.put("feeling", fe);

				database.insert("test", null, values);

				Toast.makeText(MainActivity.this, "添加成功！！！", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				if (database!=null) {//只有数据库不为空才可以关闭，不然会抛出空指针异常
					database.close();//关闭数据库
				}
			}
		}

	}

	// 定义删除的监听器类
	class Button04OnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			try {
				String ad = editText01.getText().toString().trim();
				/**
				 * table：代表想删除数据的表名。 whereClause：满足该whereClause子句的记录将会被删除。
				 * whereArgs：用于为whereArgs子句传入参数。 直接删除address属性值和变量ad的值对应的那条记录
				 */
				database.delete("test", "address = ?", new String[] { ad });
				Toast.makeText(MainActivity.this, "删除成功！！！", Toast.LENGTH_SHORT).show();
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

	// 定义更新的监听器类
	class Button03OnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			try {
				String ad = editText01.getText().toString().trim();

				ContentValues values = new ContentValues();
				// 存放更新后address的值
				values.put("address", ad);
				// 将address值为无锡的记录更改成和变量ad相等的值
				database.update("test", values, "address = ?", new String[] { "无锡" });
				Toast.makeText(MainActivity.this, "更新成功！！！", Toast.LENGTH_SHORT).show();
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

	// 定义查询的监听器类
	class Button02OnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			try {
				String ad = editText01.getText().toString().trim();

				// 该函数返回的是一个游标
				// 参数1为表名；参数2为表中的所有列名；参数3为要查询条件子句；参数4为用于为selection子句中占位符传入参数值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
				Cursor cursor = database.query("test", new String[] { "address", "weather", "feeling" }, "address = ?",
						new String[] { ad }, null, null, null);
				// 遍历每一条记录
				while (cursor.moveToNext()) {//循环 Cursor 取出需要的数据
					String weather = cursor.getString(cursor.getColumnIndex("weather"));// 返回列名为weather的值
					String feeling = cursor.getString(cursor.getColumnIndex("feeling"));// 返回列名为feeling的值
					Toast.makeText(MainActivity.this, "天气:" + weather + "  " + "心情:" + feeling, Toast.LENGTH_SHORT)
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
