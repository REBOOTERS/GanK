package com.engineer.reader.beans;

import java.util.List;

/**
 * Created by co-mall on 2017/5/10.
 */

public class GanHuoShell {

    /**
     * error : false
     * results : [{"_id":"59127d59421aa90c7fefdd76","createdAt":"2017-05-10T10:39:21.26Z","desc":"手机上的网页爬虫","images":["http://img.gank.io/373d837c-a136-46f4-8b20-fbbdff48b889"],"publishedAt":"2017-05-10T11:56:10.18Z","source":"web","type":"Android","url":"http://dalingge.com/2017/05/09/%E4%BD%BF%E7%94%A8Jsoup%E6%8A%93%E5%8F%96%E5%B9%B2%E8%B4%A7%E9%9B%86%E4%B8%AD%E8%90%A5%E9%97%B2%E8%AF%BB%E6%95%B0%E6%8D%AE/","used":true,"who":"Max-x"},{"_id":"59101aa9421aa90c7a8b2ade","createdAt":"2017-05-08T15:13:45.831Z","desc":"泡泡效果的索引滚动控件","images":["http://img.gank.io/ca20efbb-7751-4584-adef-c04434448719"],"publishedAt":"2017-05-09T12:13:25.467Z","source":"web","type":"Android","url":"https://github.com/cdflynn/bubble-scroll","used":true,"who":"KNOX"},{"_id":"591091e4421aa90c83a513fe","createdAt":"2017-05-08T23:42:28.112Z","desc":"底部导航tab","images":["http://img.gank.io/2c9ce9b6-135c-4cf0-a50f-a0fb7d31a6c2"],"publishedAt":"2017-05-09T12:13:25.467Z","source":"chrome","type":"Android","url":"https://github.com/bufferapp/AdaptableBottomNavigation","used":true,"who":"Jason"},{"_id":"591108f3421aa90c7a8b2ae4","createdAt":"2017-05-09T08:10:27.504Z","desc":"理解 Android 源码编译命令","publishedAt":"2017-05-09T12:13:25.467Z","source":"chrome","type":"Android","url":"http://gityuan.com/2016/03/19/android-build/","used":true,"who":"Fei"},{"_id":"591139d1421aa90c7d49ad6c","createdAt":"2017-05-09T11:38:57.628Z","desc":"透明屏幕: 装逼利器","images":["http://img.gank.io/6d5fa20e-2ae0-4a02-a9c0-8af75a7cf2ea"],"publishedAt":"2017-05-09T12:13:25.467Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/54e6286a2e73","used":true,"who":null},{"_id":"590d2d00421aa90c83a513e8","createdAt":"2017-05-06T09:55:12.318Z","desc":"自己实现一个超轻量级 JVM ","publishedAt":"2017-05-08T11:22:01.540Z","source":"chrome","type":"Android","url":"https://www.codeproject.com/Articles/24029/Home-Made-Java-Virtual-Machine","used":true,"who":"daimajia"},{"_id":"590ef4c5421aa90c83a513f0","createdAt":"2017-05-07T18:19:49.997Z","desc":"Android SO 文件的兼容和适配","publishedAt":"2017-05-08T11:22:01.540Z","source":"web","type":"Android","url":"http://blog.coderclock.com/2017/05/07/android/Android-so-files-compatibility-and-adaptation/","used":true,"who":"D_clock"},{"_id":"590f4e77421aa90c7d49ad59","createdAt":"2017-05-08T00:42:31.109Z","desc":"优雅的Snackbar","images":["http://img.gank.io/c230ed80-2a3b-443a-a831-df9453519e94"],"publishedAt":"2017-05-08T11:22:01.540Z","source":"web","type":"Android","url":"https://github.com/TonnyL/Light","used":true,"who":"黎赵太郎"},{"_id":"590faecb421aa90c7a8b2ad5","createdAt":"2017-05-08T07:33:31.794Z","desc":"一款支持透明度的取色器，想取哪里点哪里   O(∩_∩)O","images":["http://img.gank.io/800aa39c-8c0f-4fff-96dd-892259362e0f"],"publishedAt":"2017-05-08T11:22:01.540Z","source":"web","type":"Android","url":"https://github.com/DingMouRen/ColorPicker","used":true,"who":null},{"_id":"590fdc0a421aa90c7a8b2ad7","createdAt":"2017-05-08T10:46:34.42Z","desc":"Android Java8 外置标准库~","publishedAt":"2017-05-08T11:22:01.540Z","source":"chrome","type":"Android","url":"https://github.com/retropiler/retropiler","used":true,"who":"代码家"}]
     */

    private boolean error;
    private List<GanHuo> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GanHuo> getResults() {
        return results;
    }

    public void setResults(List<GanHuo> results) {
        this.results = results;
    }
}
