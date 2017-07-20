/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.mqttretrofit.adpter;

import com.example.mqttretrofit.Call;
import com.example.mqttretrofit.utlis.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface CallAdapter<R, T> {
    Type responseType();

    T adapt(Call<R> call);

    abstract class Factory {
        public abstract CallAdapter<?, ?> get(Type returnType, Annotation[] annotations);

        /**
         * @param index
         * @param type
         * @return 返回参数类型的上限
         */
        public static Type getParameterUpperBound(int index, ParameterizedType type) {
            return Utils.getParameterUpperBound(index, type);
        }

        /**
         * @param type
         * @return 返回真实的数据类型
         */
        public static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }
    }
}
