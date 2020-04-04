

package com.majie.abmap.base;

/**
 * 监听器
 *
 *  @author majie
 */
public interface OnBaseListener {

    void onMessage(String msg);

    void onResult(int code, String msg);

    void onNoData(String type);

    void onShowData(String type);

    void close();

}
