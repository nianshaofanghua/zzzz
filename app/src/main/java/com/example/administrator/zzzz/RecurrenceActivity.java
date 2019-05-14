package com.example.administrator.zzzz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

public class RecurrenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurrence);
        String a = "{\n" +
                "  \"from\": \"backend\",\n" +
                "  \"cmd\": \"delivery\",\n" +
                "  \"order_sn\": \"RE5cd92935515e5314608469\",\n" +
                "  \"dev_no\": \"3030303030303039\",\n" +
                "  \"order_goods\": [\n" +
                "    {\n" +
                "      \"goods_id\": \"3\",\n" +
                "      \"num\": \"2\",\n" +
                "      \"run\": \"01\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        CommitOrderResultModel model;
        model = new Gson().fromJson(a, CommitOrderResultModel.class);
        openDoor(model);
        findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }


    public void initData() {
        // 打开串口操作  4个打开串口操作
        // 执行倒计时操作 实现递归调用间隔一秒
        RxTimerUtil.timer(3, new RxTimerUtil.IRxNext() {
            @Override
            public void doIntent() {
                Log.e("log", "调用打开串口方法" + count);
                count++;
            }

            @Override
            public void doComplete() {
                count = 0;
                Log.e("log", "方法结束count置0");
            }
        });

//       RxTimerUtil.startTime(3000, new RxTimerUtil.IRxLastTime() {
//           @Override
//           public void doLast(String time) {
//               Log.e("log","调用多次串口方法"+count);
//               count++;
//           }
//
//           @Override
//           public void doComplete() {
//
//           }
//       });
    }


    private ArrayList<CommitOrderResultModel.OrderGoodsBean> list;
    private ArrayList<Integer> failList;
    private int count;  // 开锁总次数
    private int tempCount = 0; //  for循环内临时开锁次数变量
    private int position = 0;              // 当前开锁商品list的下标
    private String openDoorPosition;         // 当前开锁指令
    private int rxCount = 0;  // rx开锁计数

    // 开锁
    public void openDoor(final CommitOrderResultModel model) {
        list = new ArrayList<>(); //商品列表
        failList = new ArrayList<>();  // 商品失败次数
        count = 0;
        rxCount = 0;
        //初始化开锁次数  商品失败次数 商品列表
        for (int i = 0; i < model.getOrder_goods().size(); i++) {
            list.add(model.getOrder_goods().get(i));
            failList.add(0);
            count = Integer.valueOf(model.getOrder_goods().get(i).getNum()) + count;
        }
        RxTimerUtil.timer(count, new RxTimerUtil.IRxNext() {
            @Override
            public void doIntent() {
                tempCount = 0;
                position = 0;
                rxCount = rxCount + 1;//开锁次数计数
                openDoorPosition = "";
                for (int i = 0; i < list.size(); i++) {
                    int lastTempCount = tempCount;
                    tempCount = tempCount + Integer.valueOf(list.get(i).getNum());
//当前已经开锁次数与list里应该开锁次数进行比较 得出当前应该开锁下标
                    if (rxCount >= lastTempCount && rxCount <= tempCount) {
                        position = i;
                        openDoorPosition = list.get(i).getRun();
                        break; //得到位置跳出循环防止重复赋值
                    }

                }
                String openOrder = SystemUtils.openDoor(0, openDoorPosition);

                Log.e("测试", "" + openOrder);

            }

            @Override
            public void doComplete() {
                int status = 1;
                for (int i = 0; i < failList.size(); i++) {
                    if (failList.get(i) > 0) {
                        status = 0;
                    }
                    model.getOrder_goods().get(i).setNum(failList.get(i) + "");
                }
                model.setStatus(status);
                Log.e("测试", "" + new Gson().toJson(model));
            }
        });


    }


}
