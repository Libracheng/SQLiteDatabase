package com.login__excelreader;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import sqlliteopenhelper.DBHelper;

public class MainActivity extends Activity {

	private SQLiteDatabase database;
	private DBHelper dBHelper;

	private Button button;
	private TextView register;
	private TextView find;

	private EditText editText01;
	private EditText editText02;

	private EditText logIn;
	private EditText registerPassword;
	
	private EditText findName;
	private EditText updatePassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dBHelper = new DBHelper(getApplicationContext());
		database = dBHelper.getWritableDatabase();

		button = (Button) this.findViewById(R.id.button);
		button.setOnClickListener(new LogIn_ButtonOnClickListener());

		register = (TextView) this.findViewById(R.id.register);
		register.setOnClickListener(new RegisterOnClickListener());
		
		find = (TextView)this.findViewById(R.id.find);
		find.setOnClickListener(new FindOnClickListener());

		editText01 = (EditText) this.findViewById(R.id.editText01);
		editText02 = (EditText) this.findViewById(R.id.editText02);
		
		
	}

	class LogIn_ButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			
			try {
				String logIn_Name = editText01.getText().toString().trim();
				String logIn_Password = editText02.getText().toString().trim();
				Cursor cursor = database.query("mydata", new String[] { "logIn", "registerPassword" },
						"logIn = ?", new String[] { logIn_Name }, null, null, null);
					if(cursor.moveToFirst()){//判断游标是否为空
						String password = cursor.getString(cursor.getColumnIndex("registerPassword"));
						if ( logIn_Password.equals(password)) {//注意：是用equals()方法进行比较！！！
							Intent intent = new Intent();
							intent.setClass(MainActivity.this, ExcelReader.class);
							startActivity(intent);
						}
						else {
							Toast.makeText(MainActivity.this, "密码错误，请重试！！！", Toast.LENGTH_LONG).show();
						}
					}
				else {
					Toast.makeText(MainActivity.this, "登入账号错误，请先注册！！！", Toast.LENGTH_LONG).show();
				}
             

			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (database!=null) {
					database.close();
				}
			}
			
		}
		
		/**法二：
			String logIn_Name = editText01.getText().toString().trim();
			String logIn_Password = editText02.getText().toString().trim();
			Cursor cursor = database.query("mydata", null,null,null, null, null, null);
		if (cursor.moveToFirst()){//把游标移到第一行
         do{//用do-while方法，可以先执行第一行数据，然后再往下继续。如果使用while，那么就直接从第二行开始执行，第一行就跳过了
        	 String zhanghao = cursor.getString(cursor.getColumnIndex("logIn"));
        	 String mima = cursor.getString(cursor.getColumnIndex("registerPassword"));
        	 Log.d("zhanghao",zhanghao);
        	 Log.d("mima",mima);
        	 if(logIn_Name.equals(zhanghao)&&logIn_Password.equals(mima)){
        		    Intent intent = new Intent();
				    intent.setClass(MainActivity.this, ExcelReader.class);
					startActivity(intent);
					Toast.makeText(MainActivity.this, "账号密码正确", Toast.LENGTH_SHORT).show();
                 }
        	 else if(logIn_Name.equals(zhanghao)&&!(logIn_Password.equals(mima))){
        		 Toast.makeText(MainActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
        	 }
        	 else if(!(logIn_Name.equals(zhanghao))){
        		 Toast.makeText(MainActivity.this, "账号不存在！", Toast.LENGTH_SHORT).show();
        	 }
        	 }while(cursor.moveToNext());
         }
	}
		*/
		
		/**法三：                            
		 * 先把数据库中数据放到map集合中，然后再用输入的数据与map集合进行比较
		 * 用map的话，是直接判断输入的账号是否存在数据库中，存在的话再验证密码
		  Map<String,String> map = new HashMap<String,String>();
		  String logIn_Name = editText01.getText().toString().trim();
		  String logIn_Password = editText02.getText().toString().trim();
		  Cursor cursor = database.query("registerdata", null,null,null, null, null, null);
		  if (cursor.moveToFirst()){	
				
				do{

            	 String zhanghao = cursor.getString(cursor.getColumnIndex("logIn"));
            	 String mima = cursor.getString(cursor.getColumnIndex("registerPassword"));
            	 map.put(zhanghao, mima);
            	  }while(cursor.moveToNext());
             }

			if(map.containsKey(logIn_Name)){
				if(logIn_Password.equals(map.get(logIn_Name))){
					Intent intent = new Intent();
			        intent.setClass(MainActivity.this, ExcelReader.class);
					startActivity(intent);
					Toast.makeText(MainActivity.this, "账号密码正确!", Toast.LENGTH_SHORT).show();
                }else{
                	Toast.makeText(MainActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }
           }else{
        	   Toast.makeText(MainActivity.this, "账号不存在！", Toast.LENGTH_SHORT).show();
           }
}
          */
	}

	class RegisterOnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

			final View custom_dialog_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog,
					null);
			builder.setView(custom_dialog_view); // 设置对话框显示的View对象

			builder.setTitle("请注册账号");
			builder.setIcon(R.drawable.gaoyuanyuanxiao);

			builder.setPositiveButton("注册", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

						registerPassword = (EditText) custom_dialog_view.findViewById(R.id.registerPassword);// 注意：要标注是哪个布局文件的控件
						logIn = (EditText) custom_dialog_view.findViewById(R.id.logIn);

						String li = logIn.getText().toString().trim();
						String rp = registerPassword.getText().toString().trim();

						ContentValues values = new ContentValues();
						values.put("logIn", li);
						values.put("registerPassword", rp);

						database.insert("mydata", null, values);
						Toast.makeText(MainActivity.this, "注册成功！！！", Toast.LENGTH_LONG).show();
				
				}

			});
			// 创建、并显示对话框
			final AlertDialog dialog = builder.create();
			dialog.show();

			//可以覆盖setPositiveButton中的操作
			// dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new
			// View.OnClickListener() {
			//
			// public void onClick(View v) {
			// Toast.makeText(MainActivity.this, "注册成功！！！",
			// Toast.LENGTH_LONG).show();
			// dialog.dismiss();
			// }
			// });

		}
	}
	
	class FindOnClickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

			final View custom_dialog2_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog2,
					null);
			builder.setView(custom_dialog2_view); // 设置对话框显示的View对象

			builder.setTitle("修改密码");
			builder.setIcon(R.drawable.gaoyuanyaun1xiao);

			builder.setPositiveButton("确定修改", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
						updatePassword = (EditText) custom_dialog2_view.findViewById(R.id.updatePassword);// 注意：要标注是哪个布局文件的控件
						findName = (EditText) custom_dialog2_view.findViewById(R.id.findName);
						
						String name = findName.getText().toString().trim();
						String passward = updatePassword.getText().toString().trim();
						
								ContentValues values = new ContentValues();
						        values.put("registerPassword", passward);
						        database.update("mydata", values, "logIn = ?", new String[]{name});
						        Toast.makeText(MainActivity.this, "修改成功！！！", Toast.LENGTH_LONG).show();
				}});
			// 创建、并显示对话框
			final AlertDialog dialog = builder.create();
			dialog.show();
		   }
	   }
		
	}


