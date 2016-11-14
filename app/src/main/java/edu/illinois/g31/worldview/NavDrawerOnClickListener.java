package edu.illinois.g31.worldview;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

/**
 * OnClickListener to open/close nav drawers in news feed and article viewer.
 *
 * Created by Emily on 11/14/2016.
 */

public class NavDrawerOnClickListener implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;

    public NavDrawerOnClickListener(DrawerLayout drawer)  {
        mDrawerLayout = drawer;
    }

    public void onClick(View view) {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else {
            if (mDrawerLayout != null) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        }
    }
}
