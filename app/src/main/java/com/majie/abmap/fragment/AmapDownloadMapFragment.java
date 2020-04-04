

package com.majie.abmap.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.majie.abmap.adapter.AmapDownloadCityAdapter;
import com.majie.abmap.base.BaseFragment;

/**
 * 高德离线地图
 *
 *  @author majie
 */

public class AmapDownloadMapFragment extends BaseFragment implements AdapterView.OnItemClickListener, OfflineMapManager.OfflineMapDownloadListener, AmapDownloadCityAdapter.OnClickAmapDownloadOptionsListener {
    private final static int TIME_UP = 2;
    private ListView listOffline;
    private Timer timer;
    private OfflineMapManager amapManager;
    private AmapDownloadCityAdapter cityAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_UP:
                    getList();
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listOffline = new ListView(getActivity());
        initView(listOffline);
        getData();
        return listOffline;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != timer){
            timer.cancel();
        }
    }

    @Override
    protected void initView(View view) {
        listOffline.setDividerHeight(1);
        listOffline.setOnItemClickListener(this);
    }

    private void getData() {
        amapManager = new OfflineMapManager(getActivity(), this);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TIME_UP);
            }
        };

        timer = new Timer(true);
        timer.schedule(task, 1000, 1000);
    }

    public void getList() {
        if (null != amapManager) {
            List<OfflineMapCity> list = new ArrayList<>();
            list.addAll(amapManager.getDownloadOfflineMapCityList());
            list.addAll(amapManager.getDownloadingCityList());

            if (null == cityAdapter) {
                cityAdapter = new AmapDownloadCityAdapter(getActivity(), list);
                cityAdapter.setOnClickAmapDownloadOptionsListener(this);
                listOffline.setAdapter(cityAdapter);
            } else {
                cityAdapter.setList(list, true);
                cityAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        delCity(cityAdapter.getList().get(position));

    }

    @Override
    public void onDownload(int status, int completeCode, String downName) {

    }

    @Override
    public void onCheckUpdate(boolean hasNew, String name) {

    }

    @Override
    public void onRemove(boolean success, String name, String describe) {

    }

    @Override
    public void onClickAmapDownloadOptions(String option, OfflineMapCity city) {
        if ("删除".equals(option)){
            delCity(city);
        }else if ("更新".equals(option)){
            updateCity(city);
        }else if ("开始".equals(option)){
            try {
                amapManager.downloadByCityCode(city.getCode());
            } catch (AMapException e) {
                e.printStackTrace();
                onMessage("下载失败");
            }
        }else if ("暂停".equals(option)){
            amapManager.pause();
        }
    }

    private void updateCity(OfflineMapCity city) {
        try {
            amapManager.downloadByCityCode(city.getCode());
        } catch (AMapException e) {
            e.printStackTrace();
            onMessage("更新失败");
        }
    }

    private void delCity(final OfflineMapCity city){
        showAlertDialog("温馨提示", "您要删除"+city.getCity()+"的离线地图包吗?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (null != amapManager) {
                    amapManager.remove(city.getCity());
                }
                getList();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}
