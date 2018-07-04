package com.synertone.ftpmoudle.adapter;


import com.chad.library.adapter.base.entity.SectionEntity;
import com.synertone.ftpmoudle.model.FTPPictureModel;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MySection extends SectionEntity<FTPPictureModel> {
    private boolean isMore;
    public MySection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public MySection(FTPPictureModel t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
