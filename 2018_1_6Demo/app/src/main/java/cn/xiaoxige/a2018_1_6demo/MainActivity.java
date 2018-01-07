package cn.xiaoxige.a2018_1_6demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private PointerProgress pointerProgress;
    private Button btnAdd;
    private Button btnSub;
    private PulleyRuler pulleyRuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointerProgress = (PointerProgress) findViewById(R.id.pointerProgress);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        pulleyRuler = (PulleyRuler) findViewById(R.id.pulleyRuler);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointerProgress.setProgress(pointerProgress.getProgress() + 30, true);
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointerProgress.setProgress(pointerProgress.getProgress() - 30, false);
            }
        });

        pulleyRuler.setCallback(new PulleyRuler.OnRuleCallback() {
            @Override
            public void ruleChanged(View v, int progress) {
                Log.e("TAG", "progress = " + progress);
            }
        });

    }
}
