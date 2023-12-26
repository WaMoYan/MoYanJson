package com.moyan.cn

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *
 * @ClassName: MoshiJson
 * @Description: java类作用描述
 * @Author: wangweiqiang
 * @CreateDate: 2023/12/26 20:07
 */

object MoshiJson {

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()).build()

    inline fun <reified T> toJson(src: T, indent: String = ""): String {

        try {
            val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
            return jsonAdapter.indent(indent).toJson(src)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""

    }

    inline fun <reified T> fromJson(jsonStr: String): T? {
        try {
            val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
            return jsonAdapter.fromJson(jsonStr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    inline fun <reified T> getGenericType(): Type {
        val type =
            object :
                MoshiTypeReference<T>() {}::class.java
                .genericSuperclass
                .let { it as ParameterizedType }
                .actualTypeArguments
                .first()
        return type

    }

    abstract class MoshiTypeReference<T> // 自定义的类，用来包装泛型

}