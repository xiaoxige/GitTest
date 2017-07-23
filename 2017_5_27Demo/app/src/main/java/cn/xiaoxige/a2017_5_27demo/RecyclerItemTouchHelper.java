package cn.xiaoxige.a2017_5_27demo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.Callback {
    private TestRecyclerViewMoveListener listener;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.e("TAG", "" + viewHolder.getAdapterPosition() + ", " + target.getAdapterPosition());
        if (listener != null) {
            listener.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    public void setListener(TestRecyclerViewMoveListener listener) {
        this.listener = listener;
    }
}
