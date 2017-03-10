package com.sap.fieldglass.beans;

import org.apache.catalina.cluster.ClusterMessage;
import org.apache.catalina.cluster.Member;

/**
 * Created by shivendrasrivastava on 3/9/17.
 */
public class Message implements ClusterMessage{

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Member getAddress() {
        return null;
    }

    @Override
    public void setAddress(Member member) {

    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public void setTimestamp(long l) {

    }

    @Override
    public String getUniqueId() {
        return null;
    }

    @Override
    public int getResend() {
        return 0;
    }

    @Override
    public void setResend(int i) {

    }

    @Override
    public int getCompress() {
        return 0;
    }

    @Override
    public void setCompress(int i) {

    }
}
