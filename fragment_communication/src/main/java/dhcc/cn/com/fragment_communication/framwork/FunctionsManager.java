package dhcc.cn.com.fragment_communication.framwork;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * 2017/8/15 23
 */
public class FunctionsManager {
    private HashMap<String, FunctionNoParamNoResult>   mFunctionNoParamNoResultHashMap;
    private HashMap<String, FunctionNoParamHasResult>  mFunctionNoParamHasResultHashMap;
    private HashMap<String, FunctionHasParamNoResult>  mFunctionParamNoResultHashMap;
    private HashMap<String, FunctionHasParamAndResult> mFunctionParamResultHashMap;

    private static final FunctionsManager ourInstance = new FunctionsManager();

    public static FunctionsManager getInstance() {
        return ourInstance;
    }

    private FunctionsManager() {
        mFunctionNoParamNoResultHashMap = new HashMap<>();
        mFunctionNoParamHasResultHashMap = new HashMap<>();
        mFunctionParamNoResultHashMap = new HashMap<>();
        mFunctionParamResultHashMap = new HashMap<>();
    }

    public FunctionsManager addFunction(@Nullable FunctionNoParamNoResult functionNoParamNoResult) {
        if (functionNoParamNoResult == null) {
            return this;
        }
        String functionName = functionNoParamNoResult.mFunctionName;
        if (TextUtils.isEmpty(functionName)) {
            throw new IllegalArgumentException("functionName is null or empty");
        }
        mFunctionNoParamNoResultHashMap.put(functionName, functionNoParamNoResult);
        return this;
    }

    public FunctionsManager addFunction(@Nullable FunctionHasParamAndResult functionParamResult) {
        if (functionParamResult == null) {
            return this;
        }
        String functionName = functionParamResult.mFunctionName;
        if (TextUtils.isEmpty(functionName)) {
            throw new IllegalArgumentException("functionName is null or empty");
        }
        mFunctionParamResultHashMap.put(functionName, functionParamResult);
        return this;

    }

    public FunctionsManager addFunction(@Nullable FunctionNoParamHasResult functionNoParamHasResult) {
        if (functionNoParamHasResult == null) {
            return this;
        }
        String functionName = functionNoParamHasResult.mFunctionName;
        if (TextUtils.isEmpty(functionName)) {
            throw new IllegalArgumentException("functionName is null or empty");
        }
        mFunctionNoParamHasResultHashMap.put(functionName, functionNoParamHasResult);
        return this;
    }

    public FunctionsManager addFunction(@Nullable FunctionHasParamNoResult functionParamNoResult) {
        if (functionParamNoResult == null) {
            return this;
        }
        String functionName = functionParamNoResult.mFunctionName;
        if (TextUtils.isEmpty(functionName)) {
            throw new IllegalArgumentException("functionName is null or empty");
        }
        mFunctionParamNoResultHashMap.put(functionName, functionParamNoResult);
        return this;
    }

    public void invokeFunc(String functionName) {
        FunctionNoParamNoResult functionNoParamNoResult = mFunctionNoParamNoResultHashMap.get(functionName);
        if (functionName == null) {
            throw new IllegalArgumentException("方法不存在");
        }
        functionNoParamNoResult.invoke();
    }

    public <R> void invokeFuncParamNoResult(String functionName, R data) {
        FunctionHasParamNoResult functionParamNoResult = mFunctionParamNoResultHashMap.get(functionName);
        if (functionName == null) {
            throw new IllegalArgumentException("方法不存在");
        }
        functionParamNoResult.invoke(data);
    }

    public <T, R> T invokeFuncParamResult(String functionName, R data, Class<T> c) {
        FunctionHasParamAndResult functionParamResulte = mFunctionParamResultHashMap.get(functionName);
        if (functionName == null) {
            throw new IllegalArgumentException("方法不存在");
        }
        if (c != null) {
            return c.cast(functionParamResulte.invoke(data));
        } else {
            return (T) functionParamResulte.invoke(data);
        }

    }

    public <T> T invokeFuncResult(String functionName, Class<T> c) {
        FunctionNoParamHasResult functionNoParamHasResult = mFunctionNoParamHasResultHashMap.get(functionName);
        if (functionName == null) {
            throw new IllegalArgumentException("方法不存在");
        }
        if (c != null) {
            return c.cast(functionNoParamHasResult.invoke());
        } else {
            return (T) functionNoParamHasResult.invoke();
        }
    }
}
