package me.zhennan.scp.android.usecase.endpoint;

import me.zhennan.scp.android.usecase.UseCaseCallback;

/**
 * Created by zhangzhennan on 15/5/8.
 */
public interface EndPoint {

    void readEntry(String entryId, UseCaseCallback useCaseCallback);
    void readSeries(int index, UseCaseCallback useCaseCallback);

    interface QueryDelegate{

        String get(String url, String... params) throws BadRequestException;

        String post(String url, String... params) throws BadRequestException;


        class BadRequestException extends Exception{
            public int code;
            public BadRequestException(int code, String message){
                super(message);
                this.code = code;
            }
        }
    }

    /**
     * series item
     */
    class Series{
        public String label;
        public String url;
        public int index;
    }

    /**
     * enpoint result
     */
    class EndPointResult{
        final static public int CODE_SUCCESS = 200;

        String html;
        String errorMessage;
        int code;
    }
}
