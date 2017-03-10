package com.sap.fieldglass.service.impl;

import com.sap.fieldglass.beans.Message;
import com.sap.fieldglass.service.NotificationService;
import org.apache.catalina.cluster.Member;
import org.apache.catalina.cluster.tcp.SimpleTcpCluster;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivendrasrivastava on 3/9/17.
 */
@Service("notificationService")
public class NotificationServiceImpl extends SimpleTcpCluster implements NotificationService
{

    private static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);

    private final int MAX_RETRIES = 3;

    @Async
    @Override
    public void notifyClusterNodes(Message message)
    {
        logger.info(" Notifying clusters of change in file state ");

        Member currentMember = getLocalMember();
        Member[] members = getMembers();

        List<Member> clusterNodes = getNodesToSendMessage(members, currentMember);

        for (Member member : clusterNodes) {

            logger.info("Notifying cluster "+member.getName());
            message.setAddress(member);
            message.setResend(MAX_RETRIES);
            message.setTimestamp(System.currentTimeMillis());

            send(message, member);
        }
        logger.info("Cluster notification complete");
    }

    /**
     * Retrieves Nodes to which message needs to be sent.
     * @param members
     * @param currentMember
     * @return
     */
    private List<Member> getNodesToSendMessage(Member[] members, Member currentMember)
    {
        List<Member> clusterNodes = new ArrayList<>();
        for (int i=0; i<members.length; i++)
        {
            Member member = members[i];
            if (isMemberValid(member) && isMemberValid(currentMember))
            {
                if (!member.getName().equalsIgnoreCase(currentMember.getName()))
                {
                    clusterNodes.add(member);
                }
            }

        }
        return clusterNodes;
    }

    /**
     * Check if the member is valid.
     * @param member
     * @return
     */
    private Boolean isMemberValid(Member member)
    {
        if (null != member
                && member.getName() != null
                && !member.getName().isEmpty())
        {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }


}
