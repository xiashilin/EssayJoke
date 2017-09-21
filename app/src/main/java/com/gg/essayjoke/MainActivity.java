package com.gg.essayjoke;

import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gg.baselibrary.ExceptionCrashHandler;
import com.gg.baselibrary.fixbug.FixDexManager;
import com.gg.framelibrary.BaseSkinActivity;
import com.gg.framelibrary.skin.SkinManager;
import com.gg.framelibrary.skin.SkinResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseSkinActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.btn1) Button mBtn1;
    @BindView(R.id.btn2) Button mBtn2;
    @BindView(R.id.btn3) Button mBtn3;
    @BindView(R.id.img) ImageView mImg;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar.Builder(this)
                .setText(R.id.right_text, "返回")
                .setMiddleText("标题")
                .create();

    }

    @Override
    protected void initView() {
//        Resources resources = getSkinResources("");
//        if (resources != null) {
//            //获取文件的id
//            int drawableId = resources.getIdentifier("文件名", "drawable", "包名");
//            //根据ID获取资源对象
//            Drawable drawable = resources.getDrawable(drawableId);
//        }
    }

    @Override
    protected void initDate() {


//        HttpUtils.with(this).url(ConstantValue.UrlConstant.HOME_DISCOVERY_URL)
//                .param("iid", 6152551759L).param("aid", 7)
//                .execute(new HttpCallBack<DiscoverListResult>() {
//                    @Override
//                    public void onSuccess(DiscoverListResult discoverListResult) {
//                        XLog.tag(TAG).json(new Gson().toJson(discoverListResult).toString());
//                        XLog.tag(TAG).e(discoverListResult);
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//
//                    }
//                });

//
//        IDaoSupport dao = DaoSupportFactory.getFactory().getDao(Person.class);
//        dao.insert(new Person());

//        fixDexBug();
    }

    private void fixDexBug() {

        File file = ExceptionCrashHandler.getInstance().getCrashFile();
        if (file.exists()) {
            //上传到服务器

            try {
                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file));

                char[] buffer = new char[1024];
                int len = 0;
                while ((len = fileReader.read(buffer)) != 1) {
                    String str = new String(buffer, 0, len);
                    Log.d("TAG", str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //先联网下载修复包

        //获取修复包
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");

        if (fixFile.exists()) {
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void changeSkin(SkinResource skinResource) {

    }


    @OnClick(R.id.btn1)
    public void onMBtn1Clicked() {
        Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();

        String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "skin.skin";

        SkinManager.getInstance().loadSkin(skinPath);
    }

    @OnClick(R.id.btn2)
    public void onMBtn2Clicked() {
        Toast.makeText(this, "2222", Toast.LENGTH_SHORT).show();

        SkinManager.getInstance().restoreDefault();
    }

    @OnClick(R.id.btn3)
    public void onMBtn3Clicked() {
        Toast.makeText(this, "3333", Toast.LENGTH_SHORT).show();

        startActivity(MainActivity.class);
    }

}
