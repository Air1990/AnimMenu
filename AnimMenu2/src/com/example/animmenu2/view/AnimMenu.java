package com.example.animmenu2.view;

import com.example.animmenu2.R;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

public class AnimMenu extends ViewGroup implements OnClickListener {

	private static final int LEFT_TOP = 0;
	private static final int LEFT_BOTTOM = 1;
	private static final int RIGHT_TOP = 2;
	private static final int RIGHT_BOTTOM = 3;

	private int mPosition;// 主菜单位置
	public boolean mCurrentStatus = false;// 菜单是否展开
	private int mRadius;// 菜单半径
	public View mainMenu;// 主菜单
	private OnMenuClickListener mMenuClickListener;

	// private enum Position {
	// LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
	// }

	public void setOnMenuClickListener(OnMenuClickListener listener) {
		mMenuClickListener = listener;
	}

	// 接口回调函数
	public interface OnMenuClickListener {
		public void onClick(View view, int position);
	}

	public AnimMenu(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public AnimMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public AnimMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// 设置默认值
		mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				100, getResources().getDisplayMetrics());
		mPosition = RIGHT_BOTTOM;
		// 获取设置的值
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.AnimMenu, defStyleAttr, 0);
		mRadius = (int) a.getDimension(R.styleable.AnimMenu_radius, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
						getResources().getDisplayMetrics()));
		int pos = a.getInt(R.styleable.AnimMenu_position, 3);
		switch (pos) {
		case LEFT_TOP:
			mPosition = LEFT_TOP;
			break;
		case LEFT_BOTTOM:
			mPosition = LEFT_BOTTOM;
			break;
		case RIGHT_TOP:
			mPosition = RIGHT_TOP;
			break;
		case RIGHT_BOTTOM:
			mPosition = RIGHT_BOTTOM;
			break;
		default:
			break;
		}

		a.recycle();
		Log.i("---->>>>", "mPosition=" + mPosition + "\nmRadius=" + mRadius);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		if (changed) {
			setMenuLayout();
		}
	}

	private void setMenuLayout() {
		// TODO Auto-generated method stub
		mainMenu = getChildAt(getChildCount() - 1);
		mainMenu.setOnClickListener(this);
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).setOnClickListener(this);
			int l = 0;// 主菜单X距离
			int t = 0;// 主菜单Y距离
			int width = mainMenu.getMeasuredWidth();
			int height = mainMenu.getMeasuredHeight();
			switch (mPosition) {
			case LEFT_TOP:
				l = 0;
				t = 0;
				break;
			case LEFT_BOTTOM:
				l = 0;
				t = getMeasuredHeight() - height;
				break;
			case RIGHT_TOP:
				l = getMeasuredWidth() - width;
				t = 0;
				break;
			case RIGHT_BOTTOM:
				l = getMeasuredWidth() - width;
				t = getMeasuredHeight() - height;
				break;
			}
			getChildAt(i).layout(l, t, width + l, height + t);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		double n = Math.PI / (2 * (getChildCount() - 2));
		int endX, endY;
		if (mPosition == LEFT_TOP || mPosition == LEFT_BOTTOM) {
			endX = 1;
		} else {
			endX = -1;
		}
		if (mPosition == LEFT_TOP || mPosition == RIGHT_TOP) {
			endY = 1;
		} else {
			endY = -1;
		}
		if (v.getId() == R.id.menu) {
			if (!mCurrentStatus) {
				rotateAnim(mainMenu);
				showOpenAnim(endX, endY, n);
			} else {
				rotateAnim(mainMenu);
				showCloseAnim(endX, endY, n);
			}
		} else {
			itemSelectAnim(v, endX, endY, n);
			mMenuClickListener.onClick(v,mPosition);
		}
	}

	private void itemSelectAnim(View view, int endX, int endY, double n) {
		// TODO Auto-generated method stub
		ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f, 1f).setDuration(300)
				.start();
		ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.5f, 1f).setDuration(300)
				.start();
		for (int i = 0; i < getChildCount() - 1; i++) {
			PropertyValuesHolder ph1 = PropertyValuesHolder
					.ofFloat("translationX", mRadius * (float) Math.sin(n * i)
							* endX, 0);
			PropertyValuesHolder ph2 = PropertyValuesHolder
					.ofFloat("translationY", mRadius * (float) Math.cos(n * i)
							* endY, 0);
			ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(
					getChildAt(i), ph1, ph2).setDuration(300);
			oa.setInterpolator(new BounceInterpolator());
			oa.setStartDelay(300);
			oa.start();
		}
		mCurrentStatus = false;

	}

	private void rotateAnim(View view) {
		// TODO Auto-generated method stub
		ObjectAnimator.ofFloat(view, "rotation", 0f, 360f).setDuration(300)
				.start();
	}

	private void showOpenAnim(int endX, int endY, double n) {
		// TODO Auto-generated method stub
		for (int i = 0; i < getChildCount() - 1; i++) {
			rotateAnim(getChildAt(i));
			PropertyValuesHolder ph1 = PropertyValuesHolder
					.ofFloat("translationX", 0,
							mRadius * (float) Math.sin(n * i) * endX);
			PropertyValuesHolder ph2 = PropertyValuesHolder
					.ofFloat("translationY", 0,
							mRadius * (float) Math.cos(n * i) * endY);
			ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(
					getChildAt(i), ph1, ph2).setDuration(300);
			oa.setInterpolator(new BounceInterpolator());
			oa.setStartDelay(50 * i);
			oa.start();
			// ObjectAnimator
			// .ofFloat(getChildAt(i), "translationX", 0,
			// mRadius * (float) Math.sin(n * i) * endX)
			// .setDuration(300).start();
			// ObjectAnimator
			// .ofFloat(getChildAt(i), "translationY", 0,
			// mRadius * (float) Math.cos(n * i) * endY)
			// .setDuration(300).start();
		}
		mCurrentStatus = true;
	}

	public void showCloseAnim(int endX, int endY, double n) {
		// TODO Auto-generated method stub
		for (int i = 0; i < getChildCount() - 1; i++) {
			rotateAnim(getChildAt(i));
			PropertyValuesHolder ph1 = PropertyValuesHolder
					.ofFloat("translationX", mRadius * (float) Math.sin(n * i)
							* endX, 0);
			PropertyValuesHolder ph2 = PropertyValuesHolder
					.ofFloat("translationY", mRadius * (float) Math.cos(n * i)
							* endY, 0);
			ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(
					getChildAt(i), ph1, ph2).setDuration(300);
//			oa.setInterpolator(new BounceInterpolator());
			oa.setStartDelay(50 * i);
			oa.start();
			// ObjectAnimator
			// .ofFloat(getChildAt(i), "translationX",
			// mRadius * (float) Math.sin(n * i) * endX, 0)
			// .setDuration(300).start();
			// ObjectAnimator
			// .ofFloat(getChildAt(i), "translationY",
			// mRadius * (float) Math.cos(n * i) * endY, 0)
			// .setDuration(300).start();
		}
		mCurrentStatus = false;
	}

}
