package corporation.darkshadow.courster.RecyclerDivider;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import corporation.darkshadow.courster.CsActivity;
import corporation.darkshadow.courster.Fragments.AddPostFragment;
import corporation.darkshadow.courster.Fragments.WishListFragment;
import corporation.darkshadow.courster.MainActivity;
import corporation.darkshadow.courster.MechanicalActivity;

/**
 * Created by darkshadow on 29/1/18.
 */

public class RecyclerMedicalTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private WishListFragment.ClickListener clickListener;

    public RecyclerMedicalTouchListener(Context applicationContext, final RecyclerView recyclerView, final WishListFragment.ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(applicationContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
