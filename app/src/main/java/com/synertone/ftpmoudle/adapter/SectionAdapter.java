package com.synertone.ftpmoudle.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.synertone.ftpmoudle.R;
import com.synertone.ftpmoudle.model.FTPPictureModel;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    private Context context;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(Context context,int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
        this.context=context;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        //helper.addOnClickListener(R.id.more);
    }


    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        FTPPictureModel ftpPictureModel =  item.t;
        helper.setImageFromUrl(R.id.iv,ftpPictureModel.getUrl());
        helper.addOnClickListener(R.id.iv);
    }
}
