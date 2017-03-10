package com.sap.fieldglass.service;

import com.sap.fieldglass.beans.Message;

/**
 * Created by shivendrasrivastava on 3/9/17.
 */
public interface NotificationService
{
    void notifyClusterNodes(Message message);
}
