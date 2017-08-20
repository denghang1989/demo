package dhcc.cn.com.fragment_communication.framwork;

/**
 * 2017/8/15 23
 */
public abstract class FunctionHasParamNoResult<R> extends Function {
    public FunctionHasParamNoResult(String functionName) {
        super(functionName);
    }

    public abstract void invoke(R data);
}
