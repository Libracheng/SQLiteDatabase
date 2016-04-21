package com.login__excelreader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelReader extends Activity {
	private Button button;
	private ListView listView;
	private SimpleAdapter simpleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.excelreader);

		listView = (ListView) findViewById(R.id.listView);
		simpleAdapter = new SimpleAdapter(ExcelReader.this, getData(), R.layout.excelreader,
				new String[] { "id", "time", "air_temperature", "rainfall" },
				new int[] { R.id.id, R.id.time, R.id.air_temperature, R.id.rainfall });
		listView.setAdapter(simpleAdapter);

	}

	private List<Map<String, String>> getData() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		AssetManager am = this.getAssets();// 得到资源管理类AssetManager
		InputStream is = null;
		try {
			is = am.open("data.xls");
			// 得到Excel工作簿对象
			Workbook wb = Workbook.getWorkbook(is);
			// 得到Excel工作表对象。Sheet的下标是从0开始 ，获取第一张Sheet表 。
			Sheet sheet = wb.getSheet(0);
			// 得到Excel工作表的行
			int row = sheet.getRows();
			for (int i = 1; i < row; i++) {//i不能从0开始，因为第一行是列名，从0开始的话，会把列名读进去
				// 得到Excel工作表指定行的单元格 。getCell(x,y)表示第y行的第x列
				Cell id = sheet.getCell(0, i);
				Cell time = sheet.getCell(1, i);
				Cell air_temperature = sheet.getCell(2, i);
				Cell rainfall = sheet.getCell(3, i);

				//如果把下行代码放到循环外，那么输出的数据都是一样的（最后一行的数据）
				//放到循环外面，就表示只定义了一个Map集合，它只会存储最后一次的数据，之前的数据被覆盖
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("id", id.getContents());
				map.put("time", time.getContents());
				map.put("air_temperature", air_temperature.getContents());
				map.put("rainfall", rainfall.getContents());
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

}
