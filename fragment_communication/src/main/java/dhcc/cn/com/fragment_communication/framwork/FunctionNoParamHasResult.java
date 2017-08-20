package dhcc.cn.com.fragment_communication.framwork;

/**
 * 2017/8/15 23
 */
public abstract class FunctionNoParamHasResult<T> extends Function {

    public FunctionNoParamHasResult(String functionName) {
        super(functionName);
    }

    public abstract T invoke();
}
