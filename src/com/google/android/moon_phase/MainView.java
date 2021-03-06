package com.google.android.moon_phase;

import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.widget.LinearLayout;

public class MainView extends LinearLayout implements OnGestureListener {

  private static final String TAG = "MainView";

  private Calendar mDate;
  private boolean mIsNorthernHemi;  
  private MoonView mMoonView;
    
  private GestureDetector mGestureDetector;  
      
  public MainView(Context context, AttributeSet attrs) {
    super(context, attrs);

    // To process key presses.
    setFocusable(true);
    
    setOrientation(VERTICAL);
    
    mDate = Calendar.getInstance();
    mIsNorthernHemi = true;
    mMoonView = new MoonView(context, attrs);
    addView(mMoonView, new LinearLayout.LayoutParams(
        LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    update();
    
    /*
    nextInAnimation = 
      new TranslateAnimation(1.0f, 0.0f, 0.0f, 0.0f);
    nextInAnimation.setDuration(500);
    nextOutAnimation = 
      new TranslateAnimation(0.0f, -1.0f, 0.0f, 0.0f);
    nextOutAnimation.setDuration(500);
    previousInAnimation = 
      new TranslateAnimation(-1.0f, 0.0f, 0.0f, 0.0f);
    previousInAnimation.setDuration(500);
    previousOutAnimation = 
      new TranslateAnimation(0.0f, 1.0f, 0.0f, 0.0f);
    previousOutAnimation.setDuration(500);
    */
    
    mGestureDetector = new GestureDetector(this);
  }

  public Bundle saveState() {
    Bundle map = new Bundle();

    map.putSerializable("mDate", mDate);
    map.putBoolean("mIsNorthernHemi", mIsNorthernHemi);

    return map;
  }  
  
  public void restoreState(Bundle icicle) {
    mIsNorthernHemi = icicle.getBoolean("mIsNorthernHemi");
    mDate = (Calendar) icicle.getSerializable("mDate");
    
    update();
  }  
  
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent msg) {
    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
      previous();
      
      return true;
    }

    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
      next();
      
      return true;
    }

    return super.onKeyDown(keyCode, msg);
  }
  
  public void next() {
    mDate.add(Calendar.DAY_OF_MONTH, 1);
    update();
  }

  public void previous() {
    mDate.add(Calendar.DAY_OF_MONTH, -1);           
    update();
  }
  
  public void update() {
    mMoonView.update(mDate, mIsNorthernHemi);  
  }

  public Calendar getDate() {
    return mDate;
  }
  
  public void setDate(Calendar date) {
    mDate = date;
    update();
  }

  public void setNorthernHemi(boolean bool) {
    this.mIsNorthernHemi = bool;
  }

  public boolean isNorthernHemi() {
    return mIsNorthernHemi;
  }
  
  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
      float velocityY) {
    Log.i(TAG, "onFling");
    
    int deltaX = (int) e2.getX() - (int) e1.getX();    
    boolean isNext = deltaX < 0;
    
    if (isNext) {
      next();
    } else {
      previous();
    }         
    
    return false;
  }

  public boolean dispatchTouchEvent(MotionEvent e) {
    mGestureDetector.onTouchEvent(e);
    
    return true;
  }

  // OnGestureListener methods.
  
  public boolean onDown(MotionEvent e) {
    return false;
  }
    
  public void onLongPress(MotionEvent e) {
  }

  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
      float distanceY) {
    return false;
  }

  public void onShowPress(MotionEvent e) {
  }

  public boolean onSingleTapUp(MotionEvent e) {
    return false;
  }

}
