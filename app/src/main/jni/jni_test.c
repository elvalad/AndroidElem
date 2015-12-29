//
// Created by Administrator on 2015/12/29.
//

#include "jni_test.h"
#include <stdint.h>
#include "com_example_app_androidelem_NdkJniUtils.h"

uint64_t fibonacci(unsigned int n) {
    if (n > 1) {
        return fibonacci(n - 2) + fibonacci(n - 1);
    }
    return n;
}

JNIEXPORT jstring JNICALL Java_com_example_app_androidelem_NdkJniUtils_getCLanguageString
        (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "This is just a ndk jni test!!!");
}

JNIEXPORT jlong JNICALL Java_com_example_app_androidelem_NdkJniUtils_recursiveNative
        (JNIEnv *env, jobject obj, jint n) {
    return fibonacci(n);
}