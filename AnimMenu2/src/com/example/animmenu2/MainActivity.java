package com.example.animmenu2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.animmenu2.view.AnimMenu;
import com.example.animmenu2.view.AnimMenu.OnMenuClickListener;

public class MainActivity extends Activity implements OnMenuClickListener,
		OnScrollListener {
	private ListView listView;
	private List<String> data;
	private AnimMenu animMenu1, animMenu2, animMenu3, animMenu4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		animMenu1 = (AnimMenu) this.findViewById(R.id.animMenu1);
		animMenu2 = (AnimMenu) this.findViewById(R.id.animMenu2);
		animMenu3 = (AnimMenu) this.findViewById(R.id.animMenu3);
		animMenu4 = (AnimMenu) this.findViewById(R.id.animMenu4);
		animMenu1.setOnMenuClickListener(this);
		animMenu2.setOnMenuClickListener(this);
		animMenu3.setOnMenuClickListener(this);
		animMenu4.setOnMenuClickListener(this);
		listView = (ListView) this.findViewById(R.id.list);
		listView.setOnScrollListener(this);
		data = getData();
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, data));

	}

	private List<String> getData() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		for (int i = 'A'; i <= 'Z'; i++) {
			list.add((char) i + "");
		}
		return list;
	}

	@Override
	public void onClick(View view, int position) {
		// TODO Auto-generated method stub
		String tag = view.getTag().toString();
		Toast.makeText(this, tag, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		// if (animMenu.mCurrentStatus) {
		// animMenu.onClick(animMenu.mainMenu);
		// }
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (animMenu1.mCurrentStatus) {
			animMenu1.onClick(animMenu1.mainMenu);
		}
		if (animMenu2.mCurrentStatus) {
			animMenu2.onClick(animMenu2.mainMenu);
		}
		if (animMenu3.mCurrentStatus) {
			animMenu3.onClick(animMenu3.mainMenu);
		}
		if (animMenu4.mCurrentStatus) {
			animMenu4.onClick(animMenu4.mainMenu);
		}

	}

}
