

package com.majie.abmap.listener;

import com.amap.api.services.core.SuggestionCity;

import java.util.List;

import com.majie.abmap.base.OnBaseListener;
import com.majie.abmap.model.MyPoiModel;

/**
 *  @author majie
 */

public interface OnSearchResultListener extends OnBaseListener {
    void setSearchResult(List<MyPoiModel> list);
    void setSuggestCityList(List<SuggestionCity> cities);
}
