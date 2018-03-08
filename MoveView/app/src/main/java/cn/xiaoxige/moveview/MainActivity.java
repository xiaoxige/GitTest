package cn.xiaoxige.moveview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.xiaoxige.moveview.view.ScratchView;

public class MainActivity extends Activity {

    private ScratchView svScratchCard;
    private Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svScratchCard = (ScratchView)findViewById(R.id.svScratchCard);
        btnReset = (Button)findViewById(R.id.btnReset);

        svScratchCard.setAnswerMsg("小稀革");
        svScratchCard.setFrontMsg("刮开有奖");

        svScratchCard.setListener(new ScratchView.onScratchListener() {
            @Override
            public void scratched(View v, String answer) {
                Toast.makeText(MainActivity.this, answer, Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svScratchCard.setAnswerMsg("呵呵呵");
                svScratchCard.setFrontMsg("哈哈哈");
                svScratchCard.reset();
            }
        });

    }
}
