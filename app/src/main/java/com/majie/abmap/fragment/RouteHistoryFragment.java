

package com.majie.abmap.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;

import com.majie.abmap.BApp;
import com.majie.abmap.R;
import com.majie.abmap.activity.RouteActivity;
import com.majie.abmap.adapter.RouteHistoryAdapter;
import com.majie.abmap.base.BaseFragment;
import com.majie.abmap.interacter.CacheInteracter;
import com.majie.abmap.model.MyPoiModel;
import com.majie.abmap.model.RouteHistoryModel;



public class RouteHistoryFragment extends BaseFragment implements AdapterView.OnItemClickListener, RouteHistoryAdapter.OnRouteHistoryDeleteListener {
    private ListView mListHistory;
    private TextView mTextData;
    private RouteHistoryAdapter mHistoryAdapter;

    public static RouteHistoryFragment newInstance() {
        return new RouteHistoryFragment();
    }

    public RouteHistoryFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_history, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            getData();
        }
    }

    private void getData() {
        CacheInteracter cacheInteracter = new CacheInteracter(getActivity());
        try {
            List<RouteHistoryModel> historyList = cacheInteracter.getRouteHistory();

            if (null != historyList && !historyList.isEmpty() && null != historyList.get(0)) {
                if (mHistoryAdapter == null) {
                    mHistoryAdapter = new RouteHistoryAdapter(getActivity(), historyList);
                    mHistoryAdapter.setOnRouteHistoryDeleteListener(this);
                    mListHistory.setAdapter(mHistoryAdapter);
                } else {
                    mHistoryAdapter.setList(historyList, true);
                    mHistoryAdapter.notifyDataSetChanged();
                }
            } else {
                if (mHistoryAdapter == null) {
                    mHistoryAdapter = new RouteHistoryAdapter(getActivity(), null);
                    mListHistory.setAdapter(mHistoryAdapter);
                } else {
                    mHistoryAdapter.setList(null, true);
                    mHistoryAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(View view) {
        mTextData = getView(view, R.id.text_history);
        mListHistory = getView(view, R.id.list_history);
        mListHistory.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RouteHistoryModel history = mHistoryAdapter.getList().get(position);
        MyPoiModel start = new MyPoiModel(BApp.TYPE_MAP);
        start.setName(history.getNameStart());
        start.setLatitude(history.getLatStart());
        start.setLongitude(history.getLngStart());

        MyPoiModel end = new MyPoiModel(BApp.TYPE_MAP);
        end.setName(history.getNameEnd());
        end.setLatitude(history.getLatEnd());
        end.setLongitude(history.getLngEnd());

        ((RouteActivity)getActivity()).reset(start, end);
        getData();
        
    }

    @Override
    public void onRouteHistoryDelete(RouteHistoryModel history) {
        CacheInteracter cacheInteracter = new CacheInteracter(getActivity());
        try {
            cacheInteracter.deleteRouteHistory(history);
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
