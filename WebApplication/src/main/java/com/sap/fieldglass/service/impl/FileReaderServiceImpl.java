package com.sap.fieldglass.service.impl;

import com.sap.fieldglass.beans.Message;
import com.sap.fieldglass.service.FileReaderService;
import com.sap.fieldglass.service.NotificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.io.File;
import java.nio.file.*;
import java.util.List;


/**
 * Created by shivendrasrivastava on 3/9/17.
 */
@Service("fileReaderService")
public class FileReaderServiceImpl implements FileReaderService {

    @Autowired
    private NotificationService notificationService;

    @Value(value="classpath:db/db.txt")
    private Resource fileName;

    private static final Logger logger = Logger.getLogger(FileReaderServiceImpl.class);


    @Async
    @Override
    public String watchFile()
    {
        logger.info("Started Watching file for changes");

        try
        {
            Path file = Paths.get(fileName.getURI().getPath());
            WatchService watcher = file.getFileSystem().newWatchService();
            file.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey watchKey = watcher.take();

            List<WatchEvent<?>> events = watchKey.pollEvents();

            for (WatchEvent event : events)
            {
                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY)
                {
                    logger.info(" File has been changed ");
                    notificationService.notifyClusterNodes(constructMessage());
                }
            }

        }
        catch(Exception e)
        {
            if(logger.isTraceEnabled())
            {
                logger.trace("Exception thrown ", e);
            }
        }

        return null;
    }


    /**
     * Constructs message that needs to be sent to cluster nodes.
     * @return
     */
    private Message constructMessage()
    {
        Message message = new Message();
        message.setMessage("File Changed");

        return message;
    }



}
