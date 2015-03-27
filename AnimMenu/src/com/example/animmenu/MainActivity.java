package com.example.animmenu;

import java.util.ArrayList;
import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener {
	private int[] res = { R.id.composer_camera, R.id.composer_music,
			R.id.composer_place, R.id.composer_sleep };
	private List<ImageView> data = new ArrayList<>();
	private ImageView menu;
	private boolean flag = false;
	public static final int RADIUS = 150;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		menu = (ImageView) this.findViewById(R.id.menu);
		menu.setOnClickListener(this);
		for (int re : res) {
			ImageView image = (ImageView) this.findViewById(re);
			image.setOnClickListener(this);
			data.add(image);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu:
			if (!flag) {
				showMenuAnim();
			} else {
				hideMenuAnim();
			}
			break;
		case R.id.composer_camera:

			break;
		case R.id.composer_music:

			break;
		case R.id.composer_place:

			break;
		case R.id.composer_sleep:

			break;
		}
	}

	private void showMenuAnim() {
		int n = 90 / (res.length - 1);
		for (int i = 0; i < res.length; i++) {
			ObjectAnimator oa1 = ObjectAnimator.ofFloat(data.get(i),
					"translationX", 0,
					RADIUS * (float) Math.sin(Math.toRadians(n * i)));
			oa1.setDuration(500).setInterpolator(new BounceInterpolator());
			ObjectAnimator oa2 = ObjectAnimator.ofFloat(data.get(i),
					"translationY", 0,
					RADIUS * (float) Math.cos(Math.toRadians(n * i)));
			oa2.setDuration(500).setInterpolator(new BounceInterpolator());
			AnimatorSet set = new AnimatorSet();
			set.playTogether(oa1, oa2);
			set.start();
		}
		flag = true;
	}

	private void hideMenuAnim() {
		ObjectAnimator oa1;
		ObjectAnimator oa2;
		int n = 90 / (res.length - 1);
		AnimatorSet set;
		for (int i = 0; i < res.length; i++) {
			oa1 = ObjectAnimator.ofFloat(data.get(i), "translationX",
					RADIUS * (float) Math.sin(Math.toRadians(n * i)), 0)
					.setDuration(300);
			oa2 = ObjectAnimator.ofFloat(data.get(i), "translationY",
					RADIUS * (float) Math.cos(Math.toRadians(n * i)), 0)
					.setDuration(300);
			set = new AnimatorSet();
			set.playTogether(oa1, oa2);
			set.start();
		}
		flag = false;
	}

}
