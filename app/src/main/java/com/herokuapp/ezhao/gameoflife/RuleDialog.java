package com.herokuapp.ezhao.gameoflife;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class RuleDialog extends Dialog {
    public RuleDialog(Context context) {
        super(context);
        setTitle(context.getResources().getString(R.string.rules_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rule);
    }
}
