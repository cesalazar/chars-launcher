package com.archbrey.www.letters;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;


import java.util.List;


public class GetAppList {

    //AppItem[] appItem;
    private GlobalHolder global;
    private static AppItem[] recentApps;
    public static int recentAppCount;

    public GetAppList() {

        global = new GlobalHolder();

    } //public GetAppList()

    public void initialize() {

        recentApps = new AppItem[10];
        recentAppCount = 0;
    }


    public AppItem[] addRecentApp (AppItem getAppItem) {

        int stopPosition;
        boolean newItem = true;

        if ((recentAppCount < 10) && newItem) recentAppCount++ ;  //limit number of recent apps to 10

        //set default stop position if no existing apps exist
        stopPosition = recentAppCount-1;
        //determine first if app already exists in list
        for (int inc=1; inc<recentAppCount; inc++) {
            if (recentApps[inc-1].pkgname.equals(getAppItem.pkgname))
            {
                stopPosition = inc-1;
                recentAppCount--;
            } //if (recentApps[inc].pkgname.equals(getAppItem.pkgname))
        }//for (int inc=0; inc<recentAppCount; inc++)


        //insert most recent app at the start position and adjust the other apps on the list
        for (int dec=stopPosition; dec>0; dec--) {
                recentApps[dec] = recentApps[dec-1];
        }//for (int inc=0; inc<recentAppCount; inc++)

        recentApps[0] = getAppItem;
        return recentApps;

    } //public AppItem[] setRecentApp (AppItem getAppItem)




    public AppItem[] getRecentApps () {
        return recentApps;
    } //public AppItem[] getRecentApps (AppItem getAppItem)


    public AppItem[] all_appItems(PackageManager mainPkgMgr, AppItem[] appItem){

        PackageManager PkgMgr;
        PkgMgr = mainPkgMgr;

        final Intent pkgIntent = new Intent (Intent.ACTION_MAIN,null);
        pkgIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appPkgList = PkgMgr.queryIntentActivities(pkgIntent, 0);

        appItem = new AppItem[appPkgList.size()];

        for (int inc = 0; inc<appPkgList.size(); inc++){
            appItem[inc] = new AppItem();
            appItem[inc].pkgname = appPkgList.get(inc).activityInfo.packageName;
            appItem[inc].label = appPkgList.get(inc).loadLabel(PkgMgr).toString();
            appItem[inc].name = appPkgList.get(inc).activityInfo.name;

        } //for (int inc = 0; inc<appPkgList.size(); inc++)

        new SortApps().exchange_sort(appItem);

        global.setAppItem(appItem);


        return appItem;

    } //public AppItem[] all_appItems(PackageManager mainPkgMgr, AppItem[] appItem)

    public AppItem[] filterByFirstChar (AppItem[] appItem, String Search) {

        int ArraySize = appItem.length;
        int filtercount = 0;
        String MatchValue;

        AppItem[] filteredItem;
        AppItem[] resultItem;
        filteredItem = new AppItem[ArraySize];

        for (int inc = 0; inc<ArraySize; inc++){
            MatchValue = String.valueOf(appItem[inc].label.charAt(0));
            if (Search.equals(MatchValue))
            {
                filteredItem[filtercount] = new AppItem();
                filteredItem[filtercount] = appItem[inc];
                filtercount++;
            } //(Search.equals(MatchValue))
        } //for (int inc = 0; inc<appPkgList.size(); inc++)

        resultItem = new AppItem[filtercount];
        for (int inc = 0; inc<filtercount; inc++){
                resultItem[inc] = new AppItem();
                resultItem[inc] = filteredItem[inc];
        } //for (int inc = 0; inc<filtercount; inc++)

       return resultItem;
    } // public AppItem[] filterByFirstChar (AppItem[] appItem, String Search)



    public AppItem[] filterByString (AppItem[] appItem, String Search) {

        int ArraySize = appItem.length;
        int filtercount = 0;
        String MatchValue;

        AppItem[] filteredItem;
        AppItem[] resultItem;
        filteredItem = new AppItem[ArraySize];

        for (int inc = 0; inc<ArraySize; inc++){
           // MatchValue = String.valueOf(appItem[inc].label.charAt(0));
           // if (Search.equals(MatchValue))
            if (appItem[inc].label.toLowerCase().contains(Search.toLowerCase()))
            {
                filteredItem[filtercount] = new AppItem();
                filteredItem[filtercount] = appItem[inc];
                filtercount++;
            } //(Search.equals(MatchValue))
        } //for (int inc = 0; inc<appPkgList.size(); inc++)

        resultItem = new AppItem[filtercount];
        for (int inc = 0; inc<filtercount; inc++){
            resultItem[inc] = new AppItem();
            resultItem[inc] = filteredItem[inc];
        } //for (int inc = 0; inc<filtercount; inc++)

        return resultItem;
    } // public AppItem[] filterByFirstChar (AppItem[] appItem, String Search)



} //public class GetAppList
