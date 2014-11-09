package com.example.toolbox;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ditu.R;

public class GridAdapter extends BaseAdapter {

	private Activity a;
	private List<GridItemInfo> list;
	
	public void setList(List<GridItemInfo> list) {
		this.list = list;
	}
	
	static public class GridItemInfo {
		public int imageId;
		public String label;
		
		public GridItemInfo() {
			imageId = 0;
		}

		public GridItemInfo(int imageId, String label) {
			this.imageId = imageId;
			this.label = label;
		}
		
	}

	public GridAdapter(Activity a) {
		super();
		this.a = a;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		GridItemInfo itemInfo;
            convertView = a.getLayoutInflater().inflate(R.layout.grid_item, null);
            itemInfo = new GridItemInfo();
            switch (position) {
            case 0:
                itemInfo.imageId = R.drawable.cal_icon;
                itemInfo.label = "计算器";
                break;
            case 1:
                itemInfo.imageId = R.drawable.ic_launcher;
                itemInfo.label = "弹球";
                break;
            case 2:
                itemInfo.imageId = R.drawable.chat_icon;
                itemInfo.label = "聊天室";
            case 3:
            	itemInfo.imageId = R.drawable.map_icon;
                itemInfo.label = "地图";
            case 4:
            	itemInfo.imageId = R.drawable.ball_icon;
                itemInfo.label = "立体弹球";
                break;
            }
            convertView.setTag(itemInfo);

        GridItemInfo info = list.get(position);
        if (info != null && convertView != null) {
        	((ImageView)(convertView.findViewById(R.id.appImage))).setImageResource(info.imageId);
        	((TextView)(convertView.findViewById(R.id.appLabel))).setText(info.label);
        }
        return convertView;
    }

}

