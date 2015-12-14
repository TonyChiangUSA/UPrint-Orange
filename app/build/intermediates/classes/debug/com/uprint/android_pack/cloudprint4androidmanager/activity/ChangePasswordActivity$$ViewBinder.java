// Generated code from Butter Knife. Do not modify!
package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChangePasswordActivity$$ViewBinder<T extends com.uprint.android_pack.cloudprint4androidmanager.activity.ChangePasswordActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492963, "field 'oldpas'");
    target.oldpas = finder.castView(view, 2131492963, "field 'oldpas'");
    view = finder.findRequiredView(source, 2131492964, "field 'newpass'");
    target.newpass = finder.castView(view, 2131492964, "field 'newpass'");
    view = finder.findRequiredView(source, 2131492965, "field 'newpass2'");
    target.newpass2 = finder.castView(view, 2131492965, "field 'newpass2'");
  }

  @Override public void unbind(T target) {
    target.oldpas = null;
    target.newpass = null;
    target.newpass2 = null;
  }
}
