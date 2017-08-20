package dhcc.cn.com.fragment_communication.framwork;

/**
 * 2017/8/15 23
 */
public abstract class  FunctionNoParamNoResult extends Function {

    public FunctionNoParamNoResult(String functionName) {
        super(functionName);
    }

    public abstract void invoke();
}
