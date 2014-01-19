LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := FindThatAddress
LOCAL_SRC_FILES := FindThatAddress.cpp

include $(BUILD_SHARED_LIBRARY)
