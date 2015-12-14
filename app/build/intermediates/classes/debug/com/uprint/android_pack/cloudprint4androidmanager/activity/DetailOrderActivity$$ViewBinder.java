// Generated code from Butter Knife. Do not modify!
package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailOrderActivity$$ViewBinder<T extends com.uprint.android_pack.cloudprint4androidmanager.activity.DetailOrderActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492979, "field 'tasksList'");
    target.tasksList = finder.castView(view, 2131492979, "field 'tasksList'");
    view = finder.findRequiredView(source, 2131492980, "field 'feeinfo'");
    target.feeinfo = finder.castView(view, 2131492980, "field 'feeinfo'");
    view = finder.findRequiredView(source, 2131492984, "field 'comment'");
    target.comment = finder.castView(view, 2131492984, "field 'comment'");
  }

  @Override public void unbind(T target) {
    target.tasksList = null;
    target.feeinfo = null;
    target.comment = null;
  }
}
