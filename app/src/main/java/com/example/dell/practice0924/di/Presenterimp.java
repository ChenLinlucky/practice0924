package com.example.dell.practice0924.di;

import java.lang.ref.WeakReference;

public class Presenterimp implements Icontract.ipresenter<Icontract.iview>{
    private Icontract.iview iview;
    private Moudleimp moudleimp;
    private WeakReference<Icontract.iview> iviewWeakReference;
    private WeakReference<Icontract.imoudle> imoudleWeakReference;

    @Override
    public void attchview(Icontract.iview iview) {
        this.iview = iview;
        moudleimp = new Moudleimp();
        iviewWeakReference = new WeakReference<>(iview);
        imoudleWeakReference = new WeakReference<Icontract.imoudle>(moudleimp);
    }

    @Override
    public void datachview(Icontract.iview iview) {
        iviewWeakReference.clear();
        imoudleWeakReference.clear();
    }

    @Override
            public void requestinfo() {
                moudleimp.requestdata(new Icontract.imoudle.callisten() {
                    @Override
            public void requestmsg(String s) {
                iview.data(s);
            }
        });
    }
}
