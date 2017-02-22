package com.pandaq.pandaqlib.magicrecyclerView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;

    private ArrayList<T> mDatas = new ArrayList<>();
    private View mHeaderView;
    private View mFooterView;
    private OnItemClickListener mListener;

    void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount());
    }

    public void setDatas(ArrayList<T> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addDatas(ArrayList<T> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            if (mFooterView == null) {
                //headerView footerView 都为空时
                return TYPE_NORMAL;
            } else {
                //headerView 为空 footerView 不为空时
                if (position == mDatas.size()) {
                    return TYPE_FOOTER;
                } else {
                    return TYPE_NORMAL;
                }
            }
        } else {
            if (mFooterView == null) {
                //headerView 不为空 footerView 为空时
                if (position == 0) {
                    return TYPE_HEADER;
                } else {
                    return TYPE_NORMAL;
                }
            } else {
                //headerView 不为空 footerView 也不为空时
                if (position == 0) {
                    return TYPE_HEADER;
                } else if (position == mDatas.size() + 1) {
                    return TYPE_FOOTER;
                } else {
                    return TYPE_NORMAL;
                }
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        if (mFooterView != null && viewType == TYPE_FOOTER) return new Holder(mFooterView);
        return onCreate(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            return;
        if (getItemViewType(position) == TYPE_FOOTER)
            return;
        final int pos = getRealPosition(viewHolder);
        final T data = mDatas.get(pos);
        onBind(viewHolder, pos, data);
        if (mListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, v);
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (mHeaderView != null) { //有header的时候设置header独占一行
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && holder.getLayoutPosition() == 0) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
        if (mFooterView != null) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && holder.getLayoutPosition() == getItemCount() - 1) { //最后一个 footerView
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * 获取真实的位置信息，如果添加了头部视图，数据源跟position的对应需要调整
     *
     * @param holder 绑定的holder
     * @return 显示数据的item的修正position
     */
    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            if (mFooterView == null) {
                //headerView footerView 都为空时
                return mDatas.size();
            } else {
                //headerView 为空 footerView 不为空时
                return mDatas.size() + 1;
            }
        } else {
            if (mFooterView == null) {
                //headerView 不为空 footerView 为空时
                return mDatas.size() + 1;
            } else {
                //headerView 不为空 footerView 也不为空时
                return mDatas.size() + 2;
            }
        }
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);

    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

}