package com.cndatacom.zjproject.okhttp;

public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
