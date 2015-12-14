// Generated code from Butter Knife. Do not modify!
package com.uprint.android_pack.cloudprint4androidmanager.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CPLoginActivity$$ViewBinder<T extends com.uprint.android_pack.cloudprint4androidmanager.activity.CPLoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493001, "field 'accountTextView'");
    target.accountTextView = finder.castView(view, 2131493001, "field 'accountTextView'");
    view = finder.findRequiredView(source, 2131493002, "field 'passwordTextView'");
    target.passwordTextView = finder.castView(view, 2131493002, "field 'passwordTextView'");
    view = finder.findRequiredView(source, 2131493004, "field 'logInBtn'");
    target.logInBtn = finder.castView(view, 2131493004, "field 'logInBtn'");
    view = finder.findRequiredView(source, 2131493003, "field 'forgetPaswd'");
    target.forgetPaswd = finder.castView(view, 2131493003, "field 'forgetPaswd'");
  }

  @Override public void unbind(T target) {
    target.accountTextView = null;
    target.passwordTextView = null;
    target.logInBtn = null;
    target.forgetPaswd = null;
  }
}
