package com.cndatacom.zjproject.common;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 网格列表分割线
 * Created by cdc4512 on 2018/1/2.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int width;

    private Paint paint;

    public GridItemDecoration(int width, int color) {
        this.width = width;
        paint = new Paint();
        paint.setColor(color);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count = parent.getChildCount();
        GridLayoutManager glm = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = glm.getSpanCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            if (i % spanCount != spanCount - 1) {
                Rect rect = new Rect();
                rect.set(child.getRight(), child.getTop(), child.getRight() + width, child.getBottom());
                c.drawRect(rect, paint);
            }
            if (i < count - spanCount) {
                Rect rect = new Rect();
                rect.set(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom() + width);
                c.drawRect(rect, paint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        int count = parent.getChildCount();
        GridLayoutManager glm = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = glm.getSpanCount();
        if (pos % spanCount != spanCount - 1) {
            outRect.right = width;
        }
        if (pos < count - spanCount) {
            outRect.bottom = width;
        }

    }
}
