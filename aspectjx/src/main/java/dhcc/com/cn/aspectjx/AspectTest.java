package dhcc.com.cn.aspectjx;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
//AOP
@Aspect
public class AspectTest {

    private static final String TAG = "denghang";

    @Before("execution(* dhcc.com.cn.aspectjx.LoginActivity.onCreate(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        String longString = joinPoint.toLongString();
        long time = System.currentTimeMillis();
        Log.d(TAG, "onActivityMethodBefore: " + longString + ";time:" + time);
    }

    @Before("execution(* dhcc.com.cn.aspectjx.LoginActivity.onDestroy(..))")
    public void onActiviyuMethodBefore(JoinPoint joinPoint) throws Throwable{
        String longString = joinPoint.toLongString();
        long time = System.currentTimeMillis();
        Log.d(TAG, "onActivityMethodBefore: " + longString + ";time:" + time);
    }
}