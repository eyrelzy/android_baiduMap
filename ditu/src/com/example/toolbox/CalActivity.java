package com.example.toolbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ditu.R;

public class CalActivity extends Activity {
	private Button bt_1;
	private Button bt_2;
	private Button bt_3;
	private Button bt_4;
	private Button bt_5;
	private Button bt_6;
	private Button bt_7;
	private Button bt_8;
	private Button bt_9;
	private Button bt_0;
	private Button bt_add;
	private Button bt_sub; 
	private Button bt_multiply; 
	private Button bt_divide; 
	private Button bt_back;
	private Button bt_equal; 
	private Button bt_point; 
	private Button bt_clear; 
	private EditText et_play; 
	private EditText et_pro;

	private String str_oper = "+"; // �����
	private StringBuffer str_display = new StringBuffer();; // ��ʾ
	private String str_result; // �����ʾ
	private double num1;
	private double num2;
	private boolean flag = true; // С������ؿ��ƣ�

	private boolean b_sub, b_mul, b_div; // �����ؿ���
public void init(){
	bt_0 = (Button) findViewById(R.id.bt_0);
	bt_1 = (Button) findViewById(R.id.bt_1);
	bt_2 = (Button) findViewById(R.id.bt_2);
	bt_3 = (Button) findViewById(R.id.bt_3);
	bt_4 = (Button) findViewById(R.id.bt_4);
	bt_5 = (Button) findViewById(R.id.bt_5);
	bt_6 = (Button) findViewById(R.id.bt_6);
	bt_7 = (Button) findViewById(R.id.bt_7);
	bt_8 = (Button) findViewById(R.id.bt_8);
	bt_9 = (Button) findViewById(R.id.bt_9);
	bt_add = (Button) findViewById(R.id.bt_add);
	bt_sub = (Button) findViewById(R.id.bt_sub);
	bt_multiply = (Button) findViewById(R.id.bt_multiply);
	bt_divide = (Button) findViewById(R.id.bt_divide);
	bt_back = (Button) findViewById(R.id.bt_back);
	bt_equal = (Button) findViewById(R.id.bt_equal);
	bt_point = (Button) findViewById(R.id.bt_point);
	bt_clear = (Button) findViewById(R.id.bt_clear);
	et_play = (EditText) findViewById(R.id.et);//
	et_pro = (EditText) findViewById(R.id.pro);//
}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cal);
		init();
		

		et_play.setText("0.0");

		bt_0.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("0");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("1");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("2");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_3.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("3");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_4.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("4");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_5.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("5");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_6.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("6");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_7.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("7");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_8.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("8");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_9.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_display.append("9");
				et_play.setText(str_display.toString());
				et_pro.setText(str_display.toString());
			}
		});

		bt_point.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (flag)
				{
					str_display.append(".");
					et_play.setText(str_display.toString());
					flag = false;//����������С���
				}
			}
		});

		bt_back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (str_display.length() != 0)
				{
					str_display.deleteCharAt(str_display.length() - 1);
					et_play.setText(str_display.toString());
					et_pro.setText(str_display.toString());
				}
				else{
					et_play.setText("0.0");
					et_pro.setText("");
				}
			}
		});

		bt_add.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_oper = "+";
				//��ǰ�����ݣ�����ӷ�ʱ�����浱ǰ
				if (!(str_result == null))
				{
					num1 = Double.parseDouble(str_result);
					str_result = null;
				}
				if (!(str_display.toString() == ""))
				{
					num1 = Double.parseDouble(str_display.toString());
					str_display = new StringBuffer("");
				}
				
				et_play.setText(String.valueOf(num1));

				flag = true;
			}
		});

		bt_sub.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_oper = "-";

				if (!b_sub && !(str_display.toString() == ""))
				{
					num1 = Double.parseDouble(str_display.toString());
					et_play.setText(String.valueOf(num1));
					str_display = new StringBuffer("");
					b_sub = true;
				} else
				{
					if (!(str_display.toString() == ""))
					{
						num1 -= Double.parseDouble(str_display.toString());
						str_display = new StringBuffer("");
					}
					if (!(str_result == null))
					{
						num1 = Double.parseDouble(str_result);
						str_result = null;
					}
					et_play.setText(String.valueOf(num1));
				}

				flag = true;
			}
		});

		bt_multiply.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_oper = "*";

				if (!b_mul && !(str_display.toString() == ""))
				{
					num1 = Double.parseDouble(str_display.toString());
					et_play.setText(String.valueOf(num1));
					str_display = new StringBuffer("");
					b_mul = true;
				} else
				{
					if (!(str_display.toString() == ""))
					{
						num1 *= Double.parseDouble(str_display.toString());
						str_display = new StringBuffer("");
					}
					if (!(str_result == null))
					{
						num1 = Double.parseDouble(str_result);
						str_result = null;
					}
					et_play.setText(String.valueOf(num1));
				}

				flag = true;
			}
		});

		bt_divide.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_oper = "/";

				if (!b_div && !(str_display.toString() == ""))
				{
					num1 = Double.parseDouble(str_display.toString());
					et_play.setText(String.valueOf(num1));
					str_display = new StringBuffer("");
					b_div = true;
				} else
				{
					if (!(str_result == null))
					{
						num1 = Double.parseDouble(str_result);
						str_result = null;
					}
					if (!(str_display.toString() == ""))
					{
						if (Double.parseDouble(str_display.toString()) == 0)
						{
							Toast.makeText(CalActivity.this,
									"divider zero��", Toast.LENGTH_LONG).show();
						} else
						{
							num1 /= Double.parseDouble(str_display.toString());
							str_display = new StringBuffer("");
						}
					}
					
					et_play.setText(String.valueOf(num1));
				}
				flag = true;
			}
		});

		bt_clear.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				str_oper = "+";
				str_display = new StringBuffer("");
				str_result = null;
				num1 = 0;
				num2 = 0;
				flag = true;
				b_sub = false;
				b_mul = false;
				b_div = false;
				et_play.setText("0.0");
				et_pro.setText("");
			}
		});

		bt_equal.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (str_oper.equals("+"))
				{
					num2 = Double.parseDouble(str_display.toString());
					//jni
					str_result = String.valueOf(calculateFromJNI(num1,num2, '+'));
					Toast.makeText(getApplicationContext(), str_result, Toast.LENGTH_SHORT).show();
					et_play.setText(str_result);
					str_display = new StringBuffer("");
				}

				if (str_oper.equals("-"))
				{
					num2 = Double.parseDouble(str_display.toString());
					str_result = String.valueOf(calculateFromJNI(num1,num2, '-'));
					Toast.makeText(getApplicationContext(), str_result, Toast.LENGTH_SHORT).show();
					et_play.setText(str_result);
					str_display = new StringBuffer("");
				}

				if (str_oper.equals("*"))
				{
					num2 = Double.parseDouble(str_display.toString());
					str_result = String.valueOf(calculateFromJNI(num1,num2, '*'));
					Toast.makeText(getApplicationContext(), str_result, Toast.LENGTH_SHORT).show();
					et_play.setText(str_result);
					str_display = new StringBuffer("");
				}

				if (str_oper.equals("/"))
				{
					num2 = Double.parseDouble(str_display.toString());
					if (!(num2 == 0))
					{
						str_result = String.valueOf(calculateFromJNI(num1,num2, '/'));
						Toast.makeText(getApplicationContext(), str_result, Toast.LENGTH_SHORT).show();
						et_play.setText(str_result);
					} else
					{
						Toast.makeText(CalActivity.this,
								"dividor could not be zero!", Toast.LENGTH_LONG).show();
					}
					str_display = new StringBuffer("");
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		return true;
	}
	public native double calculateFromJNI(double a, double b, char op);
	 static {
	        System.loadLibrary("Calculator-jni");
	    }

}
