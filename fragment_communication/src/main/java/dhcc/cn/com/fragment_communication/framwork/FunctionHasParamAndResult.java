package dhcc.cn.com.fragment_communication.framwork;

/**
 * 2017/8/15 23
 */
public abstract class FunctionHasParamAndResult<T,R> extends Function {
    public FunctionHasParamAndResult(String functionName) {
        super(functionName);
    }

    public abstract T invoke(R data);
}
