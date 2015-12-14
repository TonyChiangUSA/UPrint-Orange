// Generated code from Butter Knife. Do not modify!
package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MineInfoActivity$$ViewBinder<T extends com.uprint.android_pack.cloudprint4androidmanager.activity.MineInfoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493018, "field 'managerName'");
    target.managerName = finder.castView(view, 2131493018, "field 'managerName'");
    view = finder.findRequiredView(source, 2131493019, "field 'qq'");
    target.qq = finder.castView(view, 2131493019, "field 'qq'");
    view = finder.findRequiredView(source, 2131493023, "field 'managerId'");
    target.managerId = finder.castView(view, 2131493023, "field 'managerId'");
    view = finder.findRequiredView(source, 2131492994, "field 'buildingName'");
    target.buildingName = finder.castView(view, 2131492994, "field 'buildingName'");
    view = finder.findRequiredView(source, 2131493029, "field 'dorm'");
    target.dorm = finder.castView(view, 2131493029, "field 'dorm'");
    view = finder.findRequiredView(source, 2131493031, "field 'idcard'");
    target.idcard = finder.castView(view, 2131493031, "field 'idcard'");
    view = finder.findRequiredView(source, 2131493020, "field 'sex'");
    target.sex = finder.castView(view, 2131493020, "field 'sex'");
    view = finder.findRequiredView(source, 2131492961, "field 'email'");
    target.email = finder.castView(view, 2131492961, "field 'email'");
    view = finder.findRequiredView(source, 2131493027, "field 'graduated'");
    target.graduated = finder.castView(view, 2131493027, "field 'graduated'");
    view = finder.findRequiredView(source, 2131493025, "field 'alipay'");
    target.alipay = finder.castView(view, 2131493025, "field 'alipay'");
  }

  @Override public void unbind(T target) {
    target.managerName = null;
    target.qq = null;
    target.managerId = null;
    target.buildingName = null;
    target.dorm = null;
    target.idcard = null;
    target.sex = null;
    target.email = null;
    target.graduated = null;
    target.alipay = null;
  }
}
