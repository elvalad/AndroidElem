//
// Created by Administrator on 2015/12/29.
//

#include "jni_test.h"
#include "com_example_app_androidelem_NdkJniUtils.h"

JNIEXPORT jstring JNICALL Java_com_example_app_androidelem_NdkJniUtils_getCLanguageString
        (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "This is just a ndk jni test!!!");
}
