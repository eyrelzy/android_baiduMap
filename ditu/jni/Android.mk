LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Calculator-jni
LOCAL_SRC_FILES := Calculator-jni.cpp

include $(BUILD_SHARED_LIBRARY)
